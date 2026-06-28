package com.workspace.application.port.in.letter;

import java.util.UUID;
import com.workspace.domain.model.letter.Letter;

public interface ReviewLetterUseCase {
    Letter reviewLetter(Command command);

    record Command(
        UUID letterId,
        String status // approved, rejected
    ) {}
}
