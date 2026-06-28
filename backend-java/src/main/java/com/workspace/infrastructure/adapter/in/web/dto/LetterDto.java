package com.workspace.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public final class LetterDto {
    private LetterDto() {}

    public record SubmitAbsenceRequest(
        UUID workspaceMemberId,
        String description,
        UUID meetingScheduleId,
        LocalDate absenceDate
    ) {}

    public record SubmitLateRequest(
        UUID workspaceMemberId,
        String description,
        UUID meetingScheduleId,
        LocalDate lateDate,
        Instant expectedArrivalTime
    ) {}

    public record SubmitPostponeRequest(
        UUID workspaceMemberId,
        String description,
        UUID assignmentId,
        String assignmentSnapshot,
        Instant oldDeadline,
        Instant requestedDeadline
    ) {}

    public record ReviewLetterRequest(
        String status // APPROVED, REJECTED
    ) {}

    public record LetterResponse(
        UUID id,
        WorkspaceDto.WorkspaceMemberResponse workspaceMember,
        String letterType,
        String description,
        String status,
        Instant createdAt,
        Instant updatedAt
    ) {}

    public record AbsenceLetterResponse(
        UUID letterId,
        LetterResponse letter,
        MeetingDto.MeetingResponse workspaceMeetingSchedule,
        LocalDate absenceDate
    ) {}

    public record LateLetterResponse(
        UUID letterId,
        LetterResponse letter,
        MeetingDto.MeetingResponse workspaceMeetingSchedule,
        LocalDate lateDate,
        Instant expectedArrivalTime
    ) {}

    public record PostponeLetterResponse(
        UUID letterId,
        LetterResponse letter,
        AssignmentDto.AssignmentResponse assignment,
        String assignmentSnapshot,
        Instant oldDeadline,
        Instant requestedDeadline
    ) {}
}
