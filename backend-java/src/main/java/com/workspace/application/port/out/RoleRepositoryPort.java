package com.workspace.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.user.Role;

public interface RoleRepositoryPort {
    Optional<Role> findById(UUID id);
    Optional<Role> findByWorkspaceIdAndName(UUID workspaceId, String name);
    List<Role> findByWorkspaceId(UUID workspaceId);
    Role save(Role role);
    boolean existsByWorkspaceIdAndName(UUID workspaceId, String name);
    void deleteById(UUID id);
}
