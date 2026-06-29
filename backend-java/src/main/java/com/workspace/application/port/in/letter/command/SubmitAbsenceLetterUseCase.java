package com.workspace.application.port.in.letter.command;

import java.time.LocalDate;
import java.util.UUID;
import com.workspace.domain.model.letter.AbsenceLetter;

public interface SubmitAbsenceLetterUseCase {
    AbsenceLetter submitAbsenceLetter(Command command);

    record Command(
        UUID workspaceMemberId,
        String description,
        UUID meetingScheduleId,
        LocalDate absenceDate
    ) {}
}
