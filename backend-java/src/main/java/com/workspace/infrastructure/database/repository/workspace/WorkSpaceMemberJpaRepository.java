package com.workspace.infrastructure.database.repository.workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.workspace.WorkSpaceMemberEntity;

public interface WorkSpaceMemberJpaRepository extends JpaRepository<WorkSpaceMemberEntity, UUID> {
    Optional<WorkSpaceMemberEntity> findByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
    List<WorkSpaceMemberEntity> findByWorkspaceId(UUID workspaceId);
    List<WorkSpaceMemberEntity> findByUserId(UUID userId);
    boolean existsByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
}
