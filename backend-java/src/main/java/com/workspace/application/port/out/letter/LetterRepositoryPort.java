package com.workspace.application.port.out.letter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.letter.Letter;

public interface LetterRepositoryPort {
    Optional<Letter> findById(UUID id);
    List<Letter> findByWorkspaceMemberId(UUID workspaceMemberId);
    List<Letter> findByWorkspaceMemberIdAndLetterType(UUID workspaceMemberId, String letterType);
    Letter save(Letter letter);
    void deleteById(UUID id);
}
