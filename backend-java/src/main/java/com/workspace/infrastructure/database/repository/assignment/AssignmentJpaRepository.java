package com.workspace.infrastructure.database.repository.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.assignment.AssignmentEntity;

public interface AssignmentJpaRepository extends JpaRepository<AssignmentEntity, UUID> {
    Optional<AssignmentEntity> findByWorkspaceIdAndTitle(UUID workspaceId, String title);
    List<AssignmentEntity> findByWorkspaceId(UUID workspaceId);
    List<AssignmentEntity> findByCreatedById(UUID createdById);
    boolean existsByWorkspaceIdAndTitle(UUID workspaceId, String title);
}
