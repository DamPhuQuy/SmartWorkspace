package com.workspace.application.service.meeting;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.meeting.CreateMeetingScheduleUseCase;
import com.workspace.application.port.out.meeting.MeetingScheduleRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.meeting.MeetingSchedule;
import com.workspace.domain.model.workspace.Workspace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMeetingScheduleService implements CreateMeetingScheduleUseCase {

    private final MeetingScheduleRepositoryPort meetingScheduleRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;

    @Override
    @Transactional
    public MeetingSchedule createMeeting(Command command) {
        Workspace workspace = workspaceRepositoryPort.findById(command.workspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.workspaceId() + " not found"));

        if (command.endTime().isBefore(command.startTime())) {
            throw new DomainException("Meeting end time must be after start time");
        }

        if (meetingScheduleRepositoryPort.existsByWorkspaceIdAndTitle(command.workspaceId(), command.title())) {
            throw new DomainException("Meeting schedule with title '" + command.title() + "' already exists in this workspace");
        }

        MeetingSchedule meetingSchedule = MeetingSchedule.builder()
                .id(UUID.randomUUID())
                .workspace(workspace)
                .title(command.title())
                .startTime(command.startTime())
                .endTime(command.endTime())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return meetingScheduleRepositoryPort.save(meetingSchedule);
    }
}
