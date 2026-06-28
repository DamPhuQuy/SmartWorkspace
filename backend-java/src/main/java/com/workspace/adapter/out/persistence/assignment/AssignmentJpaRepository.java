package com.workspace.adapter.out.persistence.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentJpaRepository extends JpaRepository<AssignmentEntity, UUID> {
    Optional<AssignmentEntity> findByWorkspaceIdAndTitle(UUID workspaceId, String title);
    List<AssignmentEntity> findByWorkspaceId(UUID workspaceId);
    List<AssignmentEntity> findByCreatedById(UUID createdById);
    boolean existsByWorkspaceIdAndTitle(UUID workspaceId, String title);
}
