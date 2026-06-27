package com.workspace.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.workspace.domain.model.user.User;

public interface UserRepositoryPort {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    User save(User user);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    void deleteById(UUID id);
}
