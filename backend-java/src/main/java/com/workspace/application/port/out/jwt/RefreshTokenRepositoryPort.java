package com.workspace.application.port.out.jwt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.jwt.RefreshToken;

public interface RefreshTokenRepositoryPort {
    Optional<RefreshToken> findById(UUID id);
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUserId(UUID userId);
    RefreshToken save(RefreshToken refreshToken);
    void deleteById(UUID id);
}
