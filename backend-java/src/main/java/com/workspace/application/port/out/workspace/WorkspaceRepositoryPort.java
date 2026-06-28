package com.workspace.application.port.out.workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.workspace.Workspace;
import com.workspace.domain.model.workspace.WorkspaceMember;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;

public interface WorkspaceRepositoryPort {
    Optional<Workspace> findById(UUID id);
    Optional<Workspace> findBySlug(String slug);
    Optional<Workspace> findByName(String name);
    Workspace save(Workspace workspace);
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
    void deleteById(UUID id);

    // WorkspaceMember methods
    Optional<WorkspaceMember> findMemberById(UUID id);
    Optional<WorkspaceMember> findMemberByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
    List<WorkspaceMember> findMembersByWorkspaceId(UUID workspaceId);
    List<WorkspaceMember> findMembersByUserId(UUID userId);
    WorkspaceMember saveMember(WorkspaceMember workspaceMember);
    boolean existsMemberByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
    void deleteMemberById(UUID id);

    // WorkspaceMemberRole methods
    WorkspaceMemberRole saveMemberRole(WorkspaceMemberRole workspaceMemberRole);
    Optional<WorkspaceMemberRole> findMemberRoleByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId);
    List<WorkspaceMemberRole> findMemberRolesByWorkspaceMemberId(UUID workspaceMemberId);
    void deleteMemberRoleById(UUID id);
    boolean existsMemberRoleByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId);
}
