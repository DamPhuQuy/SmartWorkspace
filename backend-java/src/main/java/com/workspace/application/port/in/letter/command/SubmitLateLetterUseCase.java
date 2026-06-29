package com.workspace.application.port.in.letter.command;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import com.workspace.domain.model.letter.LateLetter;

public interface SubmitLateLetterUseCase {
    LateLetter submitLateLetter(Command command);

    record Command(
        UUID workspaceMemberId,
        String description,
        UUID meetingScheduleId,
        LocalDate lateDate,
        Instant expectedArrivalTime
    ) {}
}
