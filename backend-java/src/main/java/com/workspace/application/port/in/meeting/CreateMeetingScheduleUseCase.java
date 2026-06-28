package com.workspace.application.port.in.meeting;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.meeting.MeetingSchedule;

public interface CreateMeetingScheduleUseCase {
    MeetingSchedule createMeeting(Command command);

    record Command(
        UUID workspaceId,
        String title,
        Instant startTime,
        Instant endTime
    ) {}
}
