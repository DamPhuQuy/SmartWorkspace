package com.workspace.domain.model.workspace;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class WorkspaceMember {
    private UUID id;
    private UUID workspaceId;
    private UUID userId;
    private Instant joinedAt;

    public WorkspaceMember(UUID id, UUID workspaceId, UUID userId, Instant joinedAt) {
        if (id == null) {
            throw new IllegalArgumentException("Workspace member id is required");
        }

        if (workspaceId == null) {
            throw new IllegalArgumentException("Workspace id is required");
        }

        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }

        this.id = id;
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.joinedAt = joinedAt == null ? Instant.now() : joinedAt;
    }

    public static WorkspaceMember join(UUID workspaceId, UUID userId) {
        return new WorkspaceMember(
                UUID.randomUUID(),
                workspaceId,
                userId,
                Instant.now()
        );
    }

    public boolean belongsToWorkspace(UUID workspaceId) {
        return this.workspaceId.equals(workspaceId);
    }

    public boolean isMember(UUID userId) {
        return this.userId.equals(userId);
    }
}
