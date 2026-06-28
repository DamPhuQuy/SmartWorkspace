package com.workspace.application.port.out;

import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.user.Permission;

public interface PermissionRepositoryPort {
    Optional<Permission> findById(UUID id);
    Optional<Permission> findByName(String name);
    Permission save(Permission permission);
    boolean existsByName(String name);
    void deleteById(UUID id);
}
