package com.workspace.features.collaboration.infrastructure.web;

import com.workspace.features.collaboration.application.dto.*;
import com.workspace.features.collaboration.application.service.*;
import com.workspace.features.collaboration.infrastructure.persistence.entity.*;
import com.workspace.features.collaboration.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.collaboration.infrastructure.web.dto.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CollaborationController {

    private final CollaborationService collaborationService;

    @GetMapping("/api/tasks/{taskId}/comments")
    @PreAuthorize("hasAuthority('*') or hasAuthority('comment:read')")
    public ResponseEntity<List<CommentDto>> getComments(
            @PathVariable UUID taskId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(collaborationService.getCommentsForTask(taskId, user));
    }

    @PostMapping("/api/tasks/{taskId}/comments")
    @PreAuthorize("hasAuthority('*') or hasAuthority('comment:create')")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable UUID taskId,
            @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(collaborationService.createComment(taskId, request, user));
    }

    @GetMapping("/api/workspaces/{workspaceId}/activity-logs")
    @PreAuthorize("hasAuthority('*') or hasAuthority('activity:read')")
    public ResponseEntity<List<ActivityLogDto>> getActivityLogs(
            @PathVariable UUID workspaceId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(collaborationService.getActivityLogs(workspaceId, user));
    }
}
