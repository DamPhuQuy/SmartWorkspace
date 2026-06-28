package com.workspace.application.port.out.letter;

import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.letter.AbsenceLetter;

public interface AbsenceLetterRepositoryPort {
    Optional<AbsenceLetter> findByLetterId(UUID letterId);
    AbsenceLetter save(AbsenceLetter absenceLetter);
    void deleteByLetterId(UUID letterId);
}
