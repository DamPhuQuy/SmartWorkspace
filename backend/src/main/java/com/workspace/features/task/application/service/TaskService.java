package com.workspace.features.task.application.service;

import com.workspace.features.task.application.dto.*;
import com.workspace.features.task.application.service.*;
import com.workspace.features.task.infrastructure.persistence.entity.*;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.task.infrastructure.web.dto.*;

import com.workspace.features.project.infrastructure.persistence.entity.Project;
import com.workspace.features.project.infrastructure.persistence.jpaRepo.ProjectRepository;
import com.workspace.features.user.infrastructure.persistence.entity.User;
import com.workspace.features.user.infrastructure.persistence.jpaRepo.UserRepository;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final BoardRepository boardRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public BoardDto getBoardForProject(UUID projectId, User currentUser) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(project.getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this project");
        }

        Board board = boardRepository.findByProjectId(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found for this project"));

        List<BoardColumn> columns = boardColumnRepository.findByBoardIdOrderByPositionAsc(board.getId());
        List<BoardColumnDto> columnDtos = columns.stream()
                .map(col -> {
                    List<TaskDto> tasks = taskRepository.findByColumnId(col.getId()).stream()
                            .map(TaskDto::fromEntity)
                            .collect(Collectors.toList());
                    return BoardColumnDto.fromEntity(col, tasks);
                })
                .collect(Collectors.toList());

        return BoardDto.fromEntity(board, columnDtos);
    }

    @Transactional
    public BoardColumnDto createColumn(UUID boardId, String name, User currentUser) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(board.getProject().getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this board");
        }

        List<BoardColumn> existingColumns = boardColumnRepository.findByBoardIdOrderByPositionAsc(boardId);
        int nextPosition = existingColumns.isEmpty() ? 0 : existingColumns.get(existingColumns.size() - 1).getPosition() + 1;

        BoardColumn column = BoardColumn.builder()
                .board(board)
                .name(name)
                .position(nextPosition)
                .build();

        return BoardColumnDto.fromEntity(boardColumnRepository.save(column), List.of());
    }

    @Transactional
    public TaskDto createTask(UUID projectId, UUID columnId, CreateTaskRequest request, User currentUser) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(project.getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this project");
        }

        BoardColumn column = boardColumnRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("Column not found"));

        Set<User> assignees = new HashSet<>();
        if (request.getAssigneeIds() != null) {
            assignees.addAll(userRepository.findAllById(request.getAssigneeIds()));
        }

        Task task = Task.builder()
                .project(project)
                .column(column)
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority() != null ? request.getPriority() : "MEDIUM")
                .dueDate(request.getDueDate())
                .createdBy(currentUser)
                .assignees(assignees)
                .build();

        return TaskDto.fromEntity(taskRepository.save(task));
    }

    @Transactional
    public TaskDto updateTask(UUID taskId, UpdateTaskRequest request, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(task.getProject().getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this task");
        }

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());

        if (request.getColumnId() != null && !task.getColumn().getId().equals(request.getColumnId())) {
            BoardColumn column = boardColumnRepository.findById(request.getColumnId())
                    .orElseThrow(() -> new IllegalArgumentException("Column not found"));
            task.setColumn(column);
        }

        if (request.getAssigneeIds() != null) {
            Set<User> assignees = new HashSet<>(userRepository.findAllById(request.getAssigneeIds()));
            task.setAssignees(assignees);
        }

        return TaskDto.fromEntity(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(UUID taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(task.getProject().getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this task");
        }

        taskRepository.delete(task);
    }

    @Transactional
    public SubtaskDto createSubtask(UUID taskId, CreateSubtaskRequest request, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(task.getProject().getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this task");
        }

        List<Subtask> existingSubtasks = subtaskRepository.findByTaskIdOrderByPositionAsc(taskId);
        int nextPosition = existingSubtasks.isEmpty() ? 0 : existingSubtasks.get(existingSubtasks.size() - 1).getPosition() + 1;

        Subtask subtask = Subtask.builder()
                .task(task)
                .title(request.getTitle())
                .isCompleted(false)
                .position(nextPosition)
                .build();

        return SubtaskDto.fromEntity(subtaskRepository.save(subtask));
    }

    @Transactional
    public SubtaskDto toggleSubtask(UUID subtaskId, boolean isCompleted, User currentUser) {
        Subtask subtask = subtaskRepository.findById(subtaskId)
                .orElseThrow(() -> new IllegalArgumentException("Subtask not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(subtask.getTask().getProject().getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this subtask");
        }

        subtask.setCompleted(isCompleted);
        return SubtaskDto.fromEntity(subtaskRepository.save(subtask));
    }

    @Transactional
    public void deleteSubtask(UUID subtaskId, User currentUser) {
        Subtask subtask = subtaskRepository.findById(subtaskId)
                .orElseThrow(() -> new IllegalArgumentException("Subtask not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(subtask.getTask().getProject().getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this subtask");
        }

        subtaskRepository.delete(subtask);
    }

    public List<SubtaskDto> getSubtasks(UUID taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(task.getProject().getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this task");
        }

        return subtaskRepository.findByTaskIdOrderByPositionAsc(taskId).stream()
                .map(SubtaskDto::fromEntity)
                .collect(Collectors.toList());
    }
}
