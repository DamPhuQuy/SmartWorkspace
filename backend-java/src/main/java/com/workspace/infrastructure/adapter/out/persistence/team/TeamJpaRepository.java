package com.workspace.infrastructure.adapter.out.persistence.team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<TeamEntity, UUID> {
    Optional<TeamEntity> findByWorkspaceIdAndName(UUID workspaceId, String name);
    List<TeamEntity> findByWorkspaceId(UUID workspaceId);
    boolean existsByWorkspaceIdAndName(UUID workspaceId, String name);
}
