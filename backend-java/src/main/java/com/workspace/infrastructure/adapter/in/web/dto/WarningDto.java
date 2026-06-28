package com.workspace.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public final class WarningDto {
    private WarningDto() {}

    public record IssueWarningRequest(
        UUID workspaceMemberId,
        String warningType,
        String description
    ) {}

    public record WarningResponse(
        UUID id,
        WorkspaceDto.WorkspaceMemberResponse workspaceMember,
        String warningType,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {}
}
