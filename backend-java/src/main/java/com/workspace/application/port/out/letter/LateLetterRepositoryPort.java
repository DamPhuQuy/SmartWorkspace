package com.workspace.application.port.out.letter;

import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.letter.LateLetter;

public interface LateLetterRepositoryPort {
    Optional<LateLetter> findByLetterId(UUID letterId);
    LateLetter save(LateLetter lateLetter);
    void deleteByLetterId(UUID letterId);
}
