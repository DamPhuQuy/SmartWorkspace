package com.workspace.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public final class AssignmentDto {
    private AssignmentDto() {}

    public record CreateAssignmentRequest(
        UUID workspaceId,
        String title,
        String description,
        Instant deadline,
        UUID createdById
    ) {}

    public record AssignAssignmentRequest(
        UUID assignmentId,
        UUID workspaceMemberId
    ) {}

    public record SubmitAssignmentRequest(
        UUID assignmentAssigneeId,
        String submissionUrl
    ) {}

    public record AssignmentResponse(
        UUID id,
        UUID workspaceId,
        String title,
        String description,
        Instant deadline,
        WorkspaceDto.WorkspaceMemberResponse createdBy,
        Instant createdAt,
        Instant updatedAt
    ) {}

    public record AssigneeResponse(
        UUID id,
        UUID assignmentId,
        WorkspaceDto.WorkspaceMemberResponse workspaceMember,
        Instant assignedAt
    ) {}

    public record SubmissionResponse(
        UUID id,
        AssigneeResponse assignmentAssignee,
        String submissionUrl,
        Instant submittedAt,
        Instant createdAt,
        Instant updatedAt
    ) {}
}
