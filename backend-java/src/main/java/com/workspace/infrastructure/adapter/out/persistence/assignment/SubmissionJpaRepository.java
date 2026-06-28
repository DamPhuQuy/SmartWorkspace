package com.workspace.infrastructure.adapter.out.persistence.assignment;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionJpaRepository extends JpaRepository<SubmissionEntity, UUID> {
    List<SubmissionEntity> findByAssignmentAssigneeId(UUID assignmentAssigneeId);
}
