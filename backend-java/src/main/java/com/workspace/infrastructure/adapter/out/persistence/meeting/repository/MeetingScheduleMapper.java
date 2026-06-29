package com.workspace.infrastructure.adapter.out.persistence.meeting.repository;

import com.workspace.domain.model.meeting.MeetingSchedule;
import com.workspace.infrastructure.adapter.out.persistence.meeting.entity.MeetingScheduleEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.repository.WorkspaceMapper;


public class MeetingScheduleMapper {

    private MeetingScheduleMapper() {}

    public static MeetingSchedule toDomain(MeetingScheduleEntity entity) {
        if (entity == null) {
            return null;
        }

        return MeetingSchedule.builder()
                .id(entity.getId())
                .workspace(WorkspaceMapper.toDomain(entity.getWorkspace()))
                .title(entity.getTitle())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .location(entity.getLocation())
                .description(entity.getDescription())
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
                .workspace(WorkspaceMapper.toEntity(domain.getWorkspace()))
                .title(domain.getTitle())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .location(domain.getLocation())
                .description(domain.getDescription())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
