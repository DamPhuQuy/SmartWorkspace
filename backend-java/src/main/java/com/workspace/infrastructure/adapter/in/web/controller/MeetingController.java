package com.workspace.infrastructure.adapter.in.web.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import com.workspace.application.port.in.meeting.command.CreateMeetingScheduleUseCase;
import com.workspace.application.port.in.meeting.command.DeleteMeetingScheduleUseCase;
import com.workspace.domain.model.meeting.MeetingSchedule;
import com.workspace.infrastructure.adapter.in.web.common.ApiResponse;
import com.workspace.infrastructure.adapter.in.web.dto.MeetingDto;
import com.workspace.infrastructure.adapter.in.web.mapper.MeetingWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
@Tag(name = "Meetings", description = "Meeting scheduling and cancellation within workspaces")
public class MeetingController {

    private final CreateMeetingScheduleUseCase createMeetingScheduleUseCase;
    private final DeleteMeetingScheduleUseCase deleteMeetingScheduleUseCase;

    @PostMapping
    @Operation(summary = "Schedule a meeting", description = "Creates a new meeting schedule in a workspace")
    public ApiResponse<MeetingDto.MeetingResponse> createMeeting(@RequestBody MeetingDto.CreateMeetingRequest request) {
        CreateMeetingScheduleUseCase.Command command = new CreateMeetingScheduleUseCase.Command(
            request.workspaceId(),
            request.title(),
            request.startTime(),
            request.endTime()
        );
        MeetingSchedule meeting = createMeetingScheduleUseCase.createMeeting(command);
        return ApiResponse.success("Meeting scheduled successfully", MeetingWebMapper.toResponse(meeting));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel a meeting", description = "Permanently deletes/cancels a scheduled meeting by ID")
    public ApiResponse<Void> deleteMeeting(@PathVariable UUID id) {
        deleteMeetingScheduleUseCase.deleteMeeting(id);
        return ApiResponse.success("Meeting cancelled successfully", null);
    }
}
