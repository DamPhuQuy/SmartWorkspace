package com.workspace.application.port.out.role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.role.Role;
import com.workspace.domain.model.role.Permission;

public interface RoleRepositoryPort {
    Optional<Role> findById(UUID id);
    Optional<Role> findByWorkspaceIdAndName(UUID workspaceId, String name);
    List<Role> findByWorkspaceId(UUID workspaceId);
    Role save(Role role);
    boolean existsByWorkspaceIdAndName(UUID workspaceId, String name);
    void deleteById(UUID id);

    // Permission methods
    Optional<Permission> findPermissionById(UUID id);
    Optional<Permission> findPermissionByName(String name);
    Permission savePermission(Permission permission);
    boolean existsPermissionByName(String name);
    void deletePermissionById(UUID id);
}
