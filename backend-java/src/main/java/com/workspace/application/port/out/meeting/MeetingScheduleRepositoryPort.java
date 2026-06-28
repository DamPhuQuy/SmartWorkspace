package com.workspace.application.port.out.meeting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.meeting.MeetingSchedule;

public interface MeetingScheduleRepositoryPort {
    Optional<MeetingSchedule> findById(UUID id);
    Optional<MeetingSchedule> findByWorkspaceIdAndTitle(UUID workspaceId, String title);
    List<MeetingSchedule> findByWorkspaceId(UUID workspaceId);
    MeetingSchedule save(MeetingSchedule meetingSchedule);
    boolean existsByWorkspaceIdAndTitle(UUID workspaceId, String title);
    void deleteById(UUID id);
}
