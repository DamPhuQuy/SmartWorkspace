package com.workspace.domain.model.user;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    private UUID id;
    private User user;
    private String token;
    private Instant expiresAt;
    private Instant revokedAt;
    private Instant createdAt;
    private Instant updatedAt;
}
