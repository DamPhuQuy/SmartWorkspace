package com.workspace.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public final class TeamDto {
    private TeamDto() {}

    public record CreateTeamRequest(
        UUID workspaceId,
        String name
    ) {}

    public record AddTeamMemberRequest(
        UUID workspaceMemberId
    ) {}

    public record TeamResponse(
        UUID id,
        UUID workspaceId,
        String name,
        Instant createdAt,
        Instant updatedAt
    ) {}

    public record TeamMemberResponse(
        UUID id,
        UUID teamId,
        WorkspaceDto.WorkspaceMemberResponse workspaceMember,
        Instant joinedAt
    ) {}
}
