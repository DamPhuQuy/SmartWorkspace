package com.workspace.features.project.infrastructure.web;

import com.workspace.features.project.application.dto.*;
import com.workspace.features.project.application.service.*;
import com.workspace.features.project.infrastructure.persistence.entity.*;
import com.workspace.features.project.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.project.infrastructure.web.dto.*;

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
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/api/workspaces/{workspaceId}/projects")
    @PreAuthorize("hasAuthority('*') or hasAuthority('project:create')")
    public ResponseEntity<ProjectDto> createProject(
            @PathVariable UUID workspaceId,
            @RequestBody CreateProjectRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(projectService.createProject(workspaceId, request, user));
    }

    @GetMapping("/api/workspaces/{workspaceId}/projects")
    @PreAuthorize("hasAuthority('*') or hasAuthority('project:read')")
    public ResponseEntity<List<ProjectDto>> getProjects(
            @PathVariable UUID workspaceId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(projectService.getProjectsForWorkspace(workspaceId, user));
    }

    @DeleteMapping("/api/projects/{projectId}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('project:delete')")
    public ResponseEntity<Void> deleteProject(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User user
    ) {
        projectService.deleteProject(projectId, user);
        return ResponseEntity.noContent().build();
    }
}
