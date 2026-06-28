package com.workspace.adapter.out.persistence.letter;

import com.workspace.domain.model.letter.LateLetter;
import com.workspace.adapter.out.persistence.meeting.MeetingScheduleMapper;

public class LateLetterMapper {

    private LateLetterMapper() {}

    public static LateLetter toDomain(LateLetterEntity entity) {
        if (entity == null) {
            return null;
        }

        return LateLetter.builder()
                .letterId(entity.getLetterId())
                .letter(LetterMapper.toDomain(entity.getLetter()))
                .workspaceMeetingSchedule(MeetingScheduleMapper.toDomain(entity.getWorkspaceMeetingSchedule()))
                .lateDate(entity.getLateDate())
                .expectedArrivalTime(entity.getExpectedArrivalTime())
                .build();
    }

    public static LateLetterEntity toEntity(LateLetter domain) {
        if (domain == null) {
            return null;
        }

        return LateLetterEntity.builder()
                .letterId(domain.getLetterId())
                .letter(LetterMapper.toEntity(domain.getLetter()))
                .workspaceMeetingSchedule(MeetingScheduleMapper.toEntity(domain.getWorkspaceMeetingSchedule()))
                .lateDate(domain.getLateDate())
                .expectedArrivalTime(domain.getExpectedArrivalTime())
                .build();
    }
}
