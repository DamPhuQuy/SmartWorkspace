package com.workspace.infrastructure.adapter.in.web.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import com.workspace.application.port.in.letter.command.ReviewLetterUseCase;
import com.workspace.application.port.in.letter.command.SubmitAbsenceLetterUseCase;
import com.workspace.application.port.in.letter.command.SubmitLateLetterUseCase;
import com.workspace.application.port.in.letter.command.SubmitPostponeLetterUseCase;
import com.workspace.domain.model.letter.AbsenceLetter;
import com.workspace.domain.model.letter.LateLetter;
import com.workspace.domain.model.letter.Letter;
import com.workspace.domain.model.letter.PostponeLetter;
import com.workspace.infrastructure.adapter.in.web.common.ApiResponse;
import com.workspace.infrastructure.adapter.in.web.dto.LetterDto;
import com.workspace.infrastructure.adapter.in.web.mapper.LetterWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/letters")
@RequiredArgsConstructor
@Tag(name = "Letters", description = "Submitting and reviewing absence letters, late arrival letters, and postponement requests")
public class LetterController {

    private final SubmitAbsenceLetterUseCase submitAbsenceLetterUseCase;
    private final SubmitLateLetterUseCase submitLateLetterUseCase;
    private final SubmitPostponeLetterUseCase submitPostponeLetterUseCase;
    private final ReviewLetterUseCase reviewLetterUseCase;

    @PostMapping("/absence")
    @Operation(summary = "Submit absence letter", description = "Submits an absence request for a scheduled meeting")
    public ApiResponse<LetterDto.AbsenceLetterResponse> submitAbsence(@RequestBody LetterDto.SubmitAbsenceRequest request) {
        SubmitAbsenceLetterUseCase.Command command = new SubmitAbsenceLetterUseCase.Command(
            request.workspaceMemberId(),
            request.description(),
            request.meetingScheduleId(),
            request.absenceDate()
        );
        AbsenceLetter absenceLetter = submitAbsenceLetterUseCase.submitAbsenceLetter(command);
        return ApiResponse.success("Absence letter submitted successfully", LetterWebMapper.toAbsenceResponse(absenceLetter));
    }

    @PostMapping("/late")
    @Operation(summary = "Submit late arrival letter", description = "Submits a request/notification for late arrival to a scheduled meeting")
    public ApiResponse<LetterDto.LateLetterResponse> submitLate(@RequestBody LetterDto.SubmitLateRequest request) {
        SubmitLateLetterUseCase.Command command = new SubmitLateLetterUseCase.Command(
            request.workspaceMemberId(),
            request.description(),
            request.meetingScheduleId(),
            request.lateDate(),
            request.expectedArrivalTime()
        );
        LateLetter lateLetter = submitLateLetterUseCase.submitLateLetter(command);
        return ApiResponse.success("Late letter submitted successfully", LetterWebMapper.toLateResponse(lateLetter));
    }

    @PostMapping("/postpone")
    @Operation(summary = "Submit postpone request", description = "Submits a request to extend/postpone an assignment deadline")
    public ApiResponse<LetterDto.PostponeLetterResponse> submitPostpone(@RequestBody LetterDto.SubmitPostponeRequest request) {
        SubmitPostponeLetterUseCase.Command command = new SubmitPostponeLetterUseCase.Command(
            request.workspaceMemberId(),
            request.description(),
            request.assignmentId(),
            request.assignmentSnapshot(),
            request.oldDeadline(),
            request.requestedDeadline()
        );
        PostponeLetter postponeLetter = submitPostponeLetterUseCase.submitPostponeLetter(command);
        return ApiResponse.success("Postpone request submitted successfully", LetterWebMapper.toPostponeResponse(postponeLetter));
    }

    @PostMapping("/{id}/review")
    @Operation(summary = "Review a letter", description = "Approves or rejects a submitted letter by ID")
    public ApiResponse<LetterDto.LetterResponse> reviewLetter(
            @PathVariable UUID id,
            @RequestBody LetterDto.ReviewLetterRequest request) {
        ReviewLetterUseCase.Command command = new ReviewLetterUseCase.Command(
            id,
            request.status()
        );
        Letter letter = reviewLetterUseCase.reviewLetter(command);
        return ApiResponse.success("Letter reviewed successfully", LetterWebMapper.toResponse(letter));
    }
}
