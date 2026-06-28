package com.workspace.application.port.out;

import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.letter.PostponeLetter;

public interface PostponeLetterRepositoryPort {
    Optional<PostponeLetter> findByLetterId(UUID letterId);
    PostponeLetter save(PostponeLetter postponeLetter);
    void deleteByLetterId(UUID letterId);
}
