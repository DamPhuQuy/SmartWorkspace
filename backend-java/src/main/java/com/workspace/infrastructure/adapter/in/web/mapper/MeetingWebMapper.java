package com.workspace.infrastructure.adapter.in.web.mapper;

import java.util.UUID;
import com.workspace.domain.model.meeting.MeetingSchedule;
import com.workspace.infrastructure.adapter.in.web.dto.MeetingDto;

public class MeetingWebMapper {

    private MeetingWebMapper() {}

    public static MeetingDto.MeetingResponse toResponse(MeetingSchedule meeting) {
        if (meeting == null) {
            return null;
        }

        UUID workspaceId = meeting.getWorkspace() != null ? meeting.getWorkspace().getId() : null;

        return new MeetingDto.MeetingResponse(
            meeting.getId(),
            workspaceId,
            meeting.getTitle(),
            meeting.getStartTime(),
            meeting.getEndTime(),
            meeting.getCreatedAt(),
            meeting.getUpdatedAt()
        );
    }
}
