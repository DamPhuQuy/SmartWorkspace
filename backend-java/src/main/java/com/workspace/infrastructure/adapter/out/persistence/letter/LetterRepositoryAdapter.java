package com.workspace.infrastructure.adapter.out.persistence.letter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.letter.LetterRepositoryPort;
import com.workspace.domain.model.letter.Letter;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LetterRepositoryAdapter implements LetterRepositoryPort {

    private final LetterJpaRepository letterJpaRepository;

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
                .collect(Collectors.toList());
    }

    @Override
    public List<Letter> findByWorkspaceMemberIdAndLetterType(UUID workspaceMemberId, String letterType) {
        return letterJpaRepository.findByWorkspaceMemberIdAndLetterType(workspaceMemberId, letterType)
                .stream()
                .map(LetterMapper::toDomain)
                .collect(Collectors.toList());
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
}
