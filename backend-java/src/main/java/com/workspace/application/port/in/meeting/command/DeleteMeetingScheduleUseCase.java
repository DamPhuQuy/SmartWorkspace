package com.workspace.application.port.in.meeting.command;

import java.util.UUID;

public interface DeleteMeetingScheduleUseCase {
    void deleteMeeting(UUID id);
}
