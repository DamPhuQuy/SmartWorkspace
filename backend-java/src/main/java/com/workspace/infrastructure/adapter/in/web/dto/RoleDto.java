package com.workspace.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public final class RoleDto {
    private RoleDto() {}

    public record CreateRoleRequest(
        UUID workspaceId,
        String name
    ) {}

    public record AssignRoleRequest(
        UUID workspaceMemberId,
        UUID roleId
    ) {}

    public record RoleResponse(
        UUID id,
        UUID workspaceId,
        String name,
        Instant createdAt,
        Instant updatedAt
    ) {}

    public record WorkspaceMemberRoleResponse(
        UUID id,
        UUID workspaceMemberId,
        RoleResponse role
    ) {}
}
