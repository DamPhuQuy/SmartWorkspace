package com.workspace.application.port.out.user;

import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.user.User;
import com.workspace.domain.model.user.UserProfile;

public interface UserRepositoryPort {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    User save(User user);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    void deleteById(UUID id);

    // UserProfile methods
    Optional<UserProfile> findProfileByUserId(UUID userId);
    UserProfile saveProfile(UserProfile userProfile);
    void deleteProfileByUserId(UUID userId);
}
