package com.workspace.infrastructure.database.adapter.out.letter;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.workspace.application.port.out.PostponeLetterRepositoryPort;
import com.workspace.domain.model.letter.PostponeLetter;
import com.workspace.infrastructure.database.entity.letter.PostponeLetterEntity;
import com.workspace.infrastructure.database.mapper.letter.PostponeLetterMapper;
import com.workspace.infrastructure.database.repository.letter.PostponeLetterJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostponeLetterRepositoryAdapter implements PostponeLetterRepositoryPort {

    private final PostponeLetterJpaRepository postponeLetterJpaRepository;

    @Override
    public Optional<PostponeLetter> findByLetterId(UUID letterId) {
        return postponeLetterJpaRepository.findById(letterId)
                .map(PostponeLetterMapper::toDomain);
    }

    @Override
    public PostponeLetter save(PostponeLetter postponeLetter) {
        PostponeLetterEntity entity = PostponeLetterMapper.toEntity(postponeLetter);
        PostponeLetterEntity savedEntity = postponeLetterJpaRepository.save(entity);
        return PostponeLetterMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteByLetterId(UUID letterId) {
        postponeLetterJpaRepository.deleteById(letterId);
    }
}
