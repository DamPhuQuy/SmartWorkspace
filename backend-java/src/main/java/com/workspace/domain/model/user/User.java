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
public class User {
    private UUID id;
    private UserProfile userProfile;
    private String email;
    private String passwordHash;
    private String phone;
    private boolean onlineStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
