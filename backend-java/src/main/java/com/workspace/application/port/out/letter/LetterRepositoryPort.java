package com.workspace.application.port.out.letter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.letter.Letter;
import com.workspace.domain.model.letter.AbsenceLetter;
import com.workspace.domain.model.letter.LateLetter;
import com.workspace.domain.model.letter.PostponeLetter;

public interface LetterRepositoryPort {
    Optional<Letter> findById(UUID id);
    List<Letter> findByWorkspaceMemberId(UUID workspaceMemberId);
    List<Letter> findByWorkspaceMemberIdAndLetterType(UUID workspaceMemberId, String letterType);
    Letter save(Letter letter);
    void deleteById(UUID id);

    // AbsenceLetter methods
    Optional<AbsenceLetter> findAbsenceByLetterId(UUID letterId);
    AbsenceLetter saveAbsence(AbsenceLetter absenceLetter);
    void deleteAbsenceByLetterId(UUID letterId);

    // LateLetter methods
    Optional<LateLetter> findLateByLetterId(UUID letterId);
    LateLetter saveLate(LateLetter lateLetter);
    void deleteLateByLetterId(UUID letterId);

    // PostponeLetter methods
    Optional<PostponeLetter> findPostponeByLetterId(UUID letterId);
    PostponeLetter savePostpone(PostponeLetter postponeLetter);
    void deletePostponeByLetterId(UUID letterId);
}
