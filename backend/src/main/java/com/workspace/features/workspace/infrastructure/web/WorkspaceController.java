package com.workspace.features.workspace.infrastructure.web;

import com.workspace.features.workspace.application.dto.*;
import com.workspace.features.workspace.application.service.*;
import com.workspace.features.workspace.infrastructure.persistence.entity.*;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.workspace.infrastructure.web.dto.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<WorkspaceDto> createWorkspace(
            @RequestBody CreateWorkspaceRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(workspaceService.createWorkspace(request, user));
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDto>> getWorkspaces(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(workspaceService.getWorkspacesForUser(user));
    }

    @GetMapping("/{slug}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('workspace:read')")
    public ResponseEntity<WorkspaceDto> getWorkspaceBySlug(
            @PathVariable String slug,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(workspaceService.getWorkspaceBySlug(slug, user));
    }

    @GetMapping("/{id}/members")
    @PreAuthorize("hasAuthority('*') or hasAuthority('workspace:read')")
    public ResponseEntity<List<WorkspaceMemberDto>> getWorkspaceMembers(
            @PathVariable("id") UUID workspaceId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(workspaceService.getWorkspaceMembers(workspaceId, user));
    }

    @PostMapping("/{id}/members/invite")
    @PreAuthorize("hasAuthority('*') or hasAuthority('workspace:invite')")
    public ResponseEntity<WorkspaceMemberDto> inviteMember(
            @PathVariable("id") UUID workspaceId,
            @RequestBody InviteMemberRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(workspaceService.inviteMember(workspaceId, request, user));
    }

    @PutMapping("/{id}/members/{memberId}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('member:update')")
    public ResponseEntity<WorkspaceMemberDto> updateMemberRole(
            @PathVariable("id") UUID workspaceId,
            @PathVariable UUID memberId,
            @RequestParam String roleName,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(workspaceService.updateMemberRole(workspaceId, memberId, roleName, user));
    }

    @DeleteMapping("/{id}/members/{memberId}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('member:delete')")
    public ResponseEntity<Void> removeMember(
            @PathVariable("id") UUID workspaceId,
            @PathVariable UUID memberId,
            @AuthenticationPrincipal User user
    ) {
        workspaceService.removeMember(workspaceId, memberId, user);
        return ResponseEntity.noContent().build();
    }
}
