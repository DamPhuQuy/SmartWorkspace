package com.workspace.infrastructure.database.mapper.letter;

import com.workspace.domain.model.letter.AbsenceLetter;
import com.workspace.infrastructure.database.entity.letter.AbsenceLetterEntity;
import com.workspace.infrastructure.database.mapper.meeting.MeetingScheduleMapper;

public class AbsenceLetterMapper {

    private AbsenceLetterMapper() {}

    public static AbsenceLetter toDomain(AbsenceLetterEntity entity) {
        if (entity == null) {
            return null;
        }

        return AbsenceLetter.builder()
                .letterId(entity.getLetterId())
                .letter(LetterMapper.toDomain(entity.getLetter()))
                .workspaceMeetingSchedule(MeetingScheduleMapper.toDomain(entity.getWorkspaceMeetingSchedule()))
                .absenceDate(entity.getAbsenceDate())
                .build();
    }

    public static AbsenceLetterEntity toEntity(AbsenceLetter domain) {
        if (domain == null) {
            return null;
        }

        return AbsenceLetterEntity.builder()
                .letterId(domain.getLetterId())
                .letter(LetterMapper.toEntity(domain.getLetter()))
                .workspaceMeetingSchedule(MeetingScheduleMapper.toEntity(domain.getWorkspaceMeetingSchedule()))
                .absenceDate(domain.getAbsenceDate())
                .build();
    }
}
