package com.workspace.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public final class MeetingDto {
    private MeetingDto() {}

    public record CreateMeetingRequest(
        UUID workspaceId,
        String title,
        Instant startTime,
        Instant endTime
    ) {}

    public record MeetingResponse(
        UUID id,
        UUID workspaceId,
        String title,
        Instant startTime,
        Instant endTime,
        Instant createdAt,
        Instant updatedAt
    ) {}
}
