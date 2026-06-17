package com.workspace.features.user.infrastructure.persistence.jpaRepo;

import com.workspace.features.user.application.dto.*;
import com.workspace.features.user.infrastructure.persistence.entity.*;
import com.workspace.features.user.infrastructure.persistence.jpaRepo.*;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
