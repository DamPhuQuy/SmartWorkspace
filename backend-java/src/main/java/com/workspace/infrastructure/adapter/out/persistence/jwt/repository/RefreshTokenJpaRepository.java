package com.workspace.infrastructure.adapter.out.persistence.jwt.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.jwt.entity.RefreshTokenEntity;


public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);
    List<RefreshTokenEntity> findByUserId(UUID userId);
}
