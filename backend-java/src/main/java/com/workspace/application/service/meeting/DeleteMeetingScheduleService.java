package com.workspace.application.service.meeting;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.meeting.DeleteMeetingScheduleUseCase;
import com.workspace.application.port.out.meeting.MeetingScheduleRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteMeetingScheduleService implements DeleteMeetingScheduleUseCase {

    private final MeetingScheduleRepositoryPort meetingScheduleRepositoryPort;

    @Override
    @Transactional
    public void deleteMeeting(UUID id) {
        if (!meetingScheduleRepositoryPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Meeting schedule with ID " + id + " not found");
        }
        meetingScheduleRepositoryPort.deleteById(id);
    }
}
