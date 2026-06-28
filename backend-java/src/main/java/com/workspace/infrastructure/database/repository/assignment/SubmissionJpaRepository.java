package com.workspace.infrastructure.database.repository.assignment;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.assignment.SubmissionEntity;

public interface SubmissionJpaRepository extends JpaRepository<SubmissionEntity, UUID> {
    List<SubmissionEntity> findByAssignmentAssigneeId(UUID assignmentAssigneeId);
}
