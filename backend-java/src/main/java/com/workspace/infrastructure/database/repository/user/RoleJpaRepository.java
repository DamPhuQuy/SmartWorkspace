package com.workspace.infrastructure.database.repository.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.user.RoleEntity;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByWorkspaceIdAndName(UUID workspaceId, String name);
    List<RoleEntity> findByWorkspaceId(UUID workspaceId);
    boolean existsByWorkspaceIdAndName(UUID workspaceId, String name);
}
