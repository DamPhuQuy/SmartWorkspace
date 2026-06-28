package com.workspace.infrastructure.database.adapter.out.letter;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.letter.LateLetterRepositoryPort;
import com.workspace.domain.model.letter.LateLetter;
import com.workspace.infrastructure.database.entity.letter.LateLetterEntity;
import com.workspace.infrastructure.database.mapper.letter.LateLetterMapper;
import com.workspace.infrastructure.database.repository.letter.LateLetterJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LateLetterRepositoryAdapter implements LateLetterRepositoryPort {

    private final LateLetterJpaRepository lateLetterJpaRepository;

    @Override
    public Optional<LateLetter> findByLetterId(UUID letterId) {
        return lateLetterJpaRepository.findById(letterId)
                .map(LateLetterMapper::toDomain);
    }

    @Override
    public LateLetter save(LateLetter lateLetter) {
        LateLetterEntity entity = LateLetterMapper.toEntity(lateLetter);
        LateLetterEntity savedEntity = lateLetterJpaRepository.save(entity);
        return LateLetterMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteByLetterId(UUID letterId) {
        lateLetterJpaRepository.deleteById(letterId);
    }
}
