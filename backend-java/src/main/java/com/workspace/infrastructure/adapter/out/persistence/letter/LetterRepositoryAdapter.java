package com.workspace.infrastructure.adapter.out.persistence.letter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.workspace.application.port.out.letter.LetterRepositoryPort;
import com.workspace.domain.model.letter.Letter;
import com.workspace.domain.model.letter.AbsenceLetter;
import com.workspace.domain.model.letter.LateLetter;
import com.workspace.domain.model.letter.PostponeLetter;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LetterRepositoryAdapter implements LetterRepositoryPort {

    private final LetterJpaRepository letterJpaRepository;
    private final AbsenceLetterJpaRepository absenceLetterJpaRepository;
    private final LateLetterJpaRepository lateLetterJpaRepository;
    private final PostponeLetterJpaRepository postponeLetterJpaRepository;

    @Override
    public Optional<Letter> findById(UUID id) {
        return letterJpaRepository.findById(id)
                .map(LetterMapper::toDomain);
    }

    @Override
    public List<Letter> findByWorkspaceMemberId(UUID workspaceMemberId) {
        return letterJpaRepository.findByWorkspaceMemberId(workspaceMemberId)
                .stream()
                .map(LetterMapper::toDomain)
                .toList();
    }

    @Override
    public List<Letter> findByWorkspaceMemberIdAndLetterType(UUID workspaceMemberId, String letterType) {
        return letterJpaRepository.findByWorkspaceMemberIdAndLetterType(workspaceMemberId, letterType)
                .stream()
                .map(LetterMapper::toDomain)
                .toList();
    }

    @Override
    public Letter save(Letter letter) {
        LetterEntity entity = LetterMapper.toEntity(letter);
        LetterEntity savedEntity = letterJpaRepository.save(entity);
        return LetterMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        letterJpaRepository.deleteById(id);
    }

    // AbsenceLetter methods
    @Override
    public Optional<AbsenceLetter> findAbsenceByLetterId(UUID letterId) {
        return absenceLetterJpaRepository.findById(letterId)
                .map(AbsenceLetterMapper::toDomain);
    }

    @Override
    public AbsenceLetter saveAbsence(AbsenceLetter absenceLetter) {
        AbsenceLetterEntity entity = AbsenceLetterMapper.toEntity(absenceLetter);
        AbsenceLetterEntity savedEntity = absenceLetterJpaRepository.save(entity);
        return AbsenceLetterMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteAbsenceByLetterId(UUID letterId) {
        absenceLetterJpaRepository.deleteById(letterId);
    }

    // LateLetter methods
    @Override
    public Optional<LateLetter> findLateByLetterId(UUID letterId) {
        return lateLetterJpaRepository.findById(letterId)
                .map(LateLetterMapper::toDomain);
    }

    @Override
    public LateLetter saveLate(LateLetter lateLetter) {
        LateLetterEntity entity = LateLetterMapper.toEntity(lateLetter);
        LateLetterEntity savedEntity = lateLetterJpaRepository.save(entity);
        return LateLetterMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteLateByLetterId(UUID letterId) {
        lateLetterJpaRepository.deleteById(letterId);
    }

    // PostponeLetter methods
    @Override
    public Optional<PostponeLetter> findPostponeByLetterId(UUID letterId) {
        return postponeLetterJpaRepository.findById(letterId)
                .map(PostponeLetterMapper::toDomain);
    }

    @Override
    public PostponeLetter savePostpone(PostponeLetter postponeLetter) {
        PostponeLetterEntity entity = PostponeLetterMapper.toEntity(postponeLetter);
        PostponeLetterEntity savedEntity = postponeLetterJpaRepository.save(entity);
        return PostponeLetterMapper.toDomain(savedEntity);
    }

    @Override
    public void deletePostponeByLetterId(UUID letterId) {
        postponeLetterJpaRepository.deleteById(letterId);
    }
}
