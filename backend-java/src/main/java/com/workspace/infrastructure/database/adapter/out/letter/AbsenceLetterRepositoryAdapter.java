package com.workspace.infrastructure.database.adapter.out.letter;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.letter.AbsenceLetterRepositoryPort;
import com.workspace.domain.model.letter.AbsenceLetter;
import com.workspace.infrastructure.database.entity.letter.AbsenceLetterEntity;
import com.workspace.infrastructure.database.mapper.letter.AbsenceLetterMapper;
import com.workspace.infrastructure.database.repository.letter.AbsenceLetterJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AbsenceLetterRepositoryAdapter implements AbsenceLetterRepositoryPort {

    private final AbsenceLetterJpaRepository absenceLetterJpaRepository;

    @Override
    public Optional<AbsenceLetter> findByLetterId(UUID letterId) {
        return absenceLetterJpaRepository.findById(letterId)
                .map(AbsenceLetterMapper::toDomain);
    }

    @Override
    public AbsenceLetter save(AbsenceLetter absenceLetter) {
        AbsenceLetterEntity entity = AbsenceLetterMapper.toEntity(absenceLetter);
        AbsenceLetterEntity savedEntity = absenceLetterJpaRepository.save(entity);
        return AbsenceLetterMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteByLetterId(UUID letterId) {
        absenceLetterJpaRepository.deleteById(letterId);
    }
}
