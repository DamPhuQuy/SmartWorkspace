package com.workspace.adapter.out.persistence.meeting;

import com.workspace.domain.model.meeting.MeetingSchedule;
import com.workspace.adapter.out.persistence.workspace.WorkSpaceMapper;

public class MeetingScheduleMapper {

    private MeetingScheduleMapper() {}

    public static MeetingSchedule toDomain(MeetingScheduleEntity entity) {
        if (entity == null) {
            return null;
        }

        return MeetingSchedule.builder()
                .id(entity.getId())
                .workspace(WorkSpaceMapper.toDomain(entity.getWorkspace()))
                .title(entity.getTitle())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static MeetingScheduleEntity toEntity(MeetingSchedule domain) {
        if (domain == null) {
            return null;
        }

        return MeetingScheduleEntity.builder()
                .id(domain.getId())
                .workspace(WorkSpaceMapper.toEntity(domain.getWorkspace()))
                .title(domain.getTitle())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
