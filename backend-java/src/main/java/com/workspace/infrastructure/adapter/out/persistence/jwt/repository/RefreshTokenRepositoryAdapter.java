package com.workspace.infrastructure.adapter.out.persistence.jwt.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.jwt.RefreshTokenRepositoryPort;
import com.workspace.domain.model.jwt.RefreshToken;
import com.workspace.infrastructure.adapter.out.persistence.jwt.entity.RefreshTokenEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public Optional<RefreshToken> findById(UUID id) {
        return refreshTokenJpaRepository.findById(id)
                .map(RefreshTokenMapper::toDomain);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByTokenHash(token)
                .map(RefreshTokenMapper::toDomain);
    }

    @Override
    public List<RefreshToken> findByUserId(UUID userId) {
        return refreshTokenJpaRepository.findByUserId(userId)
                .stream()
                .map(RefreshTokenMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity entity = RefreshTokenMapper.toEntity(refreshToken);
        RefreshTokenEntity savedEntity = refreshTokenJpaRepository.save(entity);
        return RefreshTokenMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        refreshTokenJpaRepository.deleteById(id);
    }
}
