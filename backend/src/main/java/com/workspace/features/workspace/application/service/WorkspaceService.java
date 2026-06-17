package com.workspace.features.workspace.application.service;

import com.workspace.features.workspace.application.dto.*;
import com.workspace.features.workspace.application.service.*;
import com.workspace.features.workspace.infrastructure.persistence.entity.*;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.workspace.infrastructure.web.dto.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import com.workspace.features.user.infrastructure.persistence.jpaRepo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public WorkspaceDto createWorkspace(CreateWorkspaceRequest request, User creator) {
        if (workspaceRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Workspace slug is already taken");
        }

        // 1. Create Workspace
        Workspace workspace = Workspace.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .createdBy(creator)
                .build();
        workspace = workspaceRepository.save(workspace);

        // 2. Create Default Roles
        Role ownerRole = Role.builder()
                .workspace(workspace)
                .name("OWNER")
                .permissions("*")
                .permissionSet(permissions("*"))
                .build();
        roleRepository.save(ownerRole);

        Role adminRole = Role.builder()
                .workspace(workspace)
                .name("ADMIN")
                .permissions("workspace:read,workspace:update,workspace:invite,member:update,member:delete,project:read,project:create,project:delete,board:update,task:read,task:create,task:update,task:delete,comment:read,comment:create,activity:read,event:read,event:create,event:delete,event:respond,notification:read,notification:update")
                .permissionSet(permissions(
                        "workspace:read", "workspace:update", "workspace:invite", "member:update", "member:delete",
                        "project:read", "project:create", "project:delete", "board:update",
                        "task:read", "task:create", "task:update", "task:delete",
                        "comment:read", "comment:create", "activity:read",
                        "event:read", "event:create", "event:delete", "event:respond",
                        "notification:read", "notification:update"
                ))
                .build();
        roleRepository.save(adminRole);

        Role memberRole = Role.builder()
                .workspace(workspace)
                .name("MEMBER")
                .permissions("workspace:read,project:read,task:read,task:create,task:update,comment:read,comment:create,event:read,event:respond,notification:read,notification:update")
                .permissionSet(permissions(
                        "workspace:read", "project:read", "task:read", "task:create", "task:update",
                        "comment:read", "comment:create", "event:read", "event:respond",
                        "notification:read", "notification:update"
                ))
                .build();
        roleRepository.save(memberRole);

        // 3. Add Creator as OWNER member
        WorkspaceMember member = WorkspaceMember.builder()
                .workspace(workspace)
                .user(creator)
                .role(ownerRole)
                .build();
        workspaceMemberRepository.save(member);

        return WorkspaceDto.fromEntity(workspace);
    }

    public List<WorkspaceDto> getWorkspacesForUser(User user) {
        return workspaceMemberRepository.findByUserId(user.getId()).stream()
                .map(WorkspaceMember::getWorkspace)
                .map(WorkspaceDto::fromEntity)
                .collect(Collectors.toList());
    }

    public WorkspaceDto getWorkspaceBySlug(String slug, User user) {
        Workspace workspace = workspaceRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspace.getId(), user.getId())) {
            throw new IllegalStateException("You are not a member of this workspace");
        }

        return WorkspaceDto.fromEntity(workspace);
    }

    public List<WorkspaceMemberDto> getWorkspaceMembers(UUID workspaceId, User user) {
        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspaceId, user.getId())) {
            throw new IllegalStateException("You are not a member of this workspace");
        }

        return workspaceMemberRepository.findByWorkspaceId(workspaceId).stream()
                .map(WorkspaceMemberDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkspaceMemberDto inviteMember(UUID workspaceId, InviteMemberRequest request, User currentUser) {
        // Check if current user is OWNER or ADMIN
        WorkspaceMember currentMember = workspaceMemberRepository.findByWorkspaceIdAndUserId(workspaceId, currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("You are not a member of this workspace"));

        if (currentMember.getRole() == null || 
            (!currentMember.getRole().getName().equals("OWNER") && !currentMember.getRole().getName().equals("ADMIN"))) {
            throw new IllegalStateException("You do not have permission to invite members");
        }

        User invitee = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with this email does not exist"));

        if (workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspaceId, invitee.getId())) {
            throw new IllegalArgumentException("User is already a member of this workspace");
        }

        Role role = roleRepository.findByWorkspaceIdAndName(workspaceId, request.getRoleName())
                .orElseThrow(() -> new IllegalArgumentException("Role not found in workspace"));

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));

        WorkspaceMember newMember = WorkspaceMember.builder()
                .workspace(workspace)
                .user(invitee)
                .role(role)
                .build();

        return WorkspaceMemberDto.fromEntity(workspaceMemberRepository.save(newMember));
    }

    @Transactional
    public WorkspaceMemberDto updateMemberRole(UUID workspaceId, UUID memberId, String roleName, User currentUser) {
        WorkspaceMember currentMember = workspaceMemberRepository.findByWorkspaceIdAndUserId(workspaceId, currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("You are not a member of this workspace"));

        if (currentMember.getRole() == null || !currentMember.getRole().getName().equals("OWNER")) {
            throw new IllegalStateException("Only the OWNER can modify member roles");
        }

        WorkspaceMember memberToUpdate = workspaceMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (!memberToUpdate.getWorkspace().getId().equals(workspaceId)) {
            throw new IllegalArgumentException("Member does not belong to this workspace");
        }

        Role newRole = roleRepository.findByWorkspaceIdAndName(workspaceId, roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        memberToUpdate.setRole(newRole);
        return WorkspaceMemberDto.fromEntity(workspaceMemberRepository.save(memberToUpdate));
    }

    @Transactional
    public void removeMember(UUID workspaceId, UUID memberId, User currentUser) {
        WorkspaceMember currentMember = workspaceMemberRepository.findByWorkspaceIdAndUserId(workspaceId, currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("You are not a member of this workspace"));

        if (currentMember.getRole() == null || 
            (!currentMember.getRole().getName().equals("OWNER") && !currentMember.getRole().getName().equals("ADMIN"))) {
            throw new IllegalStateException("You do not have permission to remove members");
        }

        WorkspaceMember memberToRemove = workspaceMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (!memberToRemove.getWorkspace().getId().equals(workspaceId)) {
            throw new IllegalArgumentException("Member does not belong to this workspace");
        }

        if (memberToRemove.getRole() != null && memberToRemove.getRole().getName().equals("OWNER")) {
            throw new IllegalStateException("The OWNER cannot be removed from the workspace");
        }

        workspaceMemberRepository.delete(memberToRemove);
    }

    private Set<Permission> permissions(String... names) {
        return Set.of(names).stream()
                .map(name -> permissionRepository.findByName(name)
                        .orElseGet(() -> permissionRepository.save(Permission.builder().name(name).build())))
                .collect(Collectors.toSet());
    }
}
