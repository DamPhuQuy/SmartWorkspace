package com.workspace.infrastructure.adapter.in.web.mapper;

import com.workspace.domain.model.letter.AbsenceLetter;
import com.workspace.domain.model.letter.LateLetter;
import com.workspace.domain.model.letter.Letter;
import com.workspace.domain.model.letter.PostponeLetter;
import com.workspace.infrastructure.adapter.in.web.dto.LetterDto;

public class LetterWebMapper {

    private LetterWebMapper() {}

    public static LetterDto.LetterResponse toResponse(Letter letter) {
        if (letter == null) {
            return null;
        }

        return new LetterDto.LetterResponse(
            letter.getId(),
            WorkspaceWebMapper.toMemberResponse(letter.getWorkspaceMember()),
            letter.getLetterType(),
            letter.getDescription(),
            letter.getStatus(),
            letter.getCreatedAt(),
            letter.getUpdatedAt()
        );
    }

    public static LetterDto.AbsenceLetterResponse toAbsenceResponse(AbsenceLetter absenceLetter) {
        if (absenceLetter == null) {
            return null;
        }

        return new LetterDto.AbsenceLetterResponse(
            absenceLetter.getLetterId(),
            toResponse(absenceLetter.getLetter()),
            MeetingWebMapper.toResponse(absenceLetter.getWorkspaceMeetingSchedule()),
            absenceLetter.getAbsenceDate()
        );
    }

    public static LetterDto.LateLetterResponse toLateResponse(LateLetter lateLetter) {
        if (lateLetter == null) {
            return null;
        }

        return new LetterDto.LateLetterResponse(
            lateLetter.getLetterId(),
            toResponse(lateLetter.getLetter()),
            MeetingWebMapper.toResponse(lateLetter.getWorkspaceMeetingSchedule()),
            lateLetter.getLateDate(),
            lateLetter.getExpectedArrivalTime()
        );
    }

    public static LetterDto.PostponeLetterResponse toPostponeResponse(PostponeLetter postponeLetter) {
        if (postponeLetter == null) {
            return null;
        }

        return new LetterDto.PostponeLetterResponse(
            postponeLetter.getLetterId(),
            toResponse(postponeLetter.getLetter()),
            AssignmentWebMapper.toResponse(postponeLetter.getAssignment()),
            postponeLetter.getAssignmentSnapshot(),
            postponeLetter.getOldDeadline(),
            postponeLetter.getRequestedDeadline()
        );
    }
}
