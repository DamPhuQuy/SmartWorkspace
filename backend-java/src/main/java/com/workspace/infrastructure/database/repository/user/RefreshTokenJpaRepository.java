package com.workspace.infrastructure.database.repository.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.database.entity.jwt.RefreshTokenEntity;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByToken(String token);
    List<RefreshTokenEntity> findByUserId(UUID userId);
}
