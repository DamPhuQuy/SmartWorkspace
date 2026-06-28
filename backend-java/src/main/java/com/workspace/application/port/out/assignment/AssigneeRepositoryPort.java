package com.workspace.application.port.out.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.assignment.Assignee;

public interface AssigneeRepositoryPort {
    Optional<Assignee> findById(UUID id);
    Optional<Assignee> findByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId);
    List<Assignee> findByAssignmentId(UUID assignmentId);
    List<Assignee> findByWorkspaceMemberId(UUID workspaceMemberId);
    Assignee save(Assignee assignee);
    boolean existsByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId);
    void deleteById(UUID id);
}
