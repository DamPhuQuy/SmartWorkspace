package com.workspace.domain.model.workspace;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class WorkspaceMemberRole {
    private UUID id;
    private UUID workspaceMemberId;
    private UUID roleId;

    public WorkspaceMemberRole(UUID id, UUID workspaceMemberId, UUID roleId) {
        if (id == null) {
            throw new IllegalArgumentException("Workspace member role id is required");
        }

        if (workspaceMemberId == null) {
            throw new IllegalArgumentException("Workspace member id is required");
        }

        if (roleId == null) {
            throw new IllegalArgumentException("Role id is required");
        }

        this.id = id;
        this.workspaceMemberId = workspaceMemberId;
        this.roleId = roleId;
    }

    public static WorkspaceMemberRole assign(UUID workspaceMemberId, UUID roleId) {
        return new WorkspaceMemberRole(
                UUID.randomUUID(),
                workspaceMemberId,
                roleId
        );
    }

    public boolean belongsToMember(UUID workspaceMemberId) {
        return this.workspaceMemberId.equals(workspaceMemberId);
    }

    public boolean hasRole(UUID roleId) {
        return this.roleId.equals(roleId);
    }
}
