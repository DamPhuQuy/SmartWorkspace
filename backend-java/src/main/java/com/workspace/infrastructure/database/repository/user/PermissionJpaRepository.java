package com.workspace.infrastructure.database.repository.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.user.PermissionEntity;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {
    Optional<PermissionEntity> findByName(String name);
    boolean existsByName(String name);
}
