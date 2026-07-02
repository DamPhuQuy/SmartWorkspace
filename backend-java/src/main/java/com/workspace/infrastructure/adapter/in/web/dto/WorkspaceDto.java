package com.workspace.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public final class WorkspaceDto {
    private WorkspaceDto() {}

    public record CreateWorkspaceRequest(
        String name,
        String slug,
        String description,
        UUID ownerId
    ) {}

    public record UpdateWorkspaceRequest(
        String name,
        String slug,
        String description
    ) {}

    public record AddMemberRequest(
        UUID userId
    ) {}

    public record WorkspaceResponse(
        UUID id,
        String name,
        String slug,
        String description,
        UUID ownerId,
        Instant createdAt,
        Instant updatedAt
    ) {}

    public record WorkspaceMemberResponse(
        UUID id,
        UUID workspaceId,
        UUID userId,
        Instant joinedAt
    ) {}
}
