package com.workspace.application.port.in.letter.command;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.letter.PostponeLetter;

public interface SubmitPostponeLetterUseCase {
    PostponeLetter submitPostponeLetter(Command command);

    record Command(
        UUID workspaceMemberId,
        String description,
        UUID assignmentId,
        String assignmentSnapshot,
        Instant oldDeadline,
        Instant requestedDeadline
    ) {}
}
