package com.workspace.application.port.out.user;

import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.user.UserProfile;

public interface UserProfileRepositoryPort {
    Optional<UserProfile> findByUserId(UUID userId);
    UserProfile save(UserProfile userProfile);
    void deleteByUserId(UUID userId);
}
