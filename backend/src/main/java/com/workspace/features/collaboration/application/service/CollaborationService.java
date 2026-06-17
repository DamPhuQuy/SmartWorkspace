package com.workspace.features.collaboration.application.service;

import com.workspace.features.collaboration.application.dto.*;
import com.workspace.features.collaboration.application.service.*;
import com.workspace.features.collaboration.infrastructure.persistence.entity.*;
import com.workspace.features.collaboration.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.collaboration.infrastructure.web.dto.*;

import com.workspace.features.task.infrastructure.persistence.entity.Task;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.TaskRepository;
import com.workspace.features.user.infrastructure.persistence.entity.User;
import com.workspace.features.workspace.infrastructure.persistence.entity.Workspace;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollaborationService {

    private final CommentRepository commentRepository;
    private final ActivityLogRepository activityLogRepository;
    private final TaskRepository taskRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public List<CommentDto> getCommentsForTask(UUID taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(task.getProject().getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this task");
        }

        return commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId).stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(UUID taskId, CreateCommentRequest request, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        Workspace workspace = task.getProject().getWorkspace();
        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspace.getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this task");
        }

        Comment comment = Comment.builder()
                .task(task)
                .user(currentUser)
                .content(request.getContent())
                .build();
        comment = commentRepository.save(comment);

        logActivity(workspace, currentUser, "CREATE_COMMENT", "TASK", taskId, 
                "Commented on task: " + task.getTitle());

        return CommentDto.fromEntity(comment);
    }

    public List<ActivityLogDto> getActivityLogs(UUID workspaceId, User currentUser) {
        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspaceId, currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this workspace");
        }

        return activityLogRepository.findByWorkspaceIdOrderByCreatedAtDesc(workspaceId).stream()
                .map(ActivityLogDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void logActivity(Workspace workspace, User user, String action, String entityType, UUID entityId, String details) {
        ActivityLog log = ActivityLog.builder()
                .workspace(workspace)
                .user(user)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .details(details)
                .build();
        activityLogRepository.save(log);
    }
}
