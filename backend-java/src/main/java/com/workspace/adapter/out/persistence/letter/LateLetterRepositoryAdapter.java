package com.workspace.adapter.out.persistence.letter;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.letter.LateLetterRepositoryPort;
import com.workspace.domain.model.letter.LateLetter;
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
