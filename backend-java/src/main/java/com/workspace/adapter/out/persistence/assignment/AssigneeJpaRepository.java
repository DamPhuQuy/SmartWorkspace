package com.workspace.adapter.out.persistence.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssigneeJpaRepository extends JpaRepository<AssigneeEntity, UUID> {
    Optional<AssigneeEntity> findByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId);
    List<AssigneeEntity> findByAssignmentId(UUID assignmentId);
    List<AssigneeEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
    boolean existsByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId);
}
