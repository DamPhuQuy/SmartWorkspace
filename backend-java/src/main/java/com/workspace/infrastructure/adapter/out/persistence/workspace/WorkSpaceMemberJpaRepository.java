package com.workspace.infrastructure.adapter.out.persistence.workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSpaceMemberJpaRepository extends JpaRepository<WorkSpaceMemberEntity, UUID> {
    Optional<WorkSpaceMemberEntity> findByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
    List<WorkSpaceMemberEntity> findByWorkspaceId(UUID workspaceId);
    List<WorkSpaceMemberEntity> findByUserId(UUID userId);
    boolean existsByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
}
