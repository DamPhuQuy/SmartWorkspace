package com.workspace.features.project.application.service;

import com.workspace.features.project.application.dto.*;
import com.workspace.features.project.application.service.*;
import com.workspace.features.project.infrastructure.persistence.entity.*;
import com.workspace.features.project.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.project.infrastructure.web.dto.*;

import com.workspace.features.task.infrastructure.persistence.entity.Board;
import com.workspace.features.task.infrastructure.persistence.entity.BoardColumn;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.BoardRepository;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.BoardColumnRepository;
import com.workspace.features.user.infrastructure.persistence.entity.User;
import com.workspace.features.workspace.infrastructure.persistence.entity.Workspace;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.WorkspaceRepository;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final BoardRepository boardRepository;
    private final BoardColumnRepository boardColumnRepository;

    @Transactional
    public ProjectDto createProject(UUID workspaceId, CreateProjectRequest request, User currentUser) {
        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspaceId, currentUser.getId())) {
            throw new IllegalStateException("You are not a member of this workspace");
        }

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));

        Project project = Project.builder()
                .workspace(workspace)
                .name(request.getName())
                .description(request.getDescription())
                .status("ACTIVE")
                .build();
        project = projectRepository.save(project);

        // Create Default Board for project
        Board board = Board.builder()
                .project(project)
                .name("Kanban Board")
                .build();
        board = boardRepository.save(board);

        // Create Default Board Columns
        BoardColumn todo = BoardColumn.builder()
                .board(board)
                .name("To Do")
                .position(0)
                .build();
        boardColumnRepository.save(todo);

        BoardColumn inProgress = BoardColumn.builder()
                .board(board)
                .name("In Progress")
                .position(1)
                .build();
        boardColumnRepository.save(inProgress);

        BoardColumn done = BoardColumn.builder()
                .board(board)
                .name("Done")
                .position(2)
                .build();
        boardColumnRepository.save(done);

        return ProjectDto.fromEntity(project);
    }

    public List<ProjectDto> getProjectsForWorkspace(UUID workspaceId, User currentUser) {
        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspaceId, currentUser.getId())) {
            throw new IllegalStateException("You are not a member of this workspace");
        }

        return projectRepository.findByWorkspaceId(workspaceId).stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProject(UUID projectId, User currentUser) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(project.getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this project");
        }

        projectRepository.delete(project);
    }
}
