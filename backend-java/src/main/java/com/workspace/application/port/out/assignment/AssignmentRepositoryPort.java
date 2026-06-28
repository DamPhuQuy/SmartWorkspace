package com.workspace.application.port.out.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.assignment.Assignment;

public interface AssignmentRepositoryPort {
    Optional<Assignment> findById(UUID id);
    Optional<Assignment> findByWorkspaceIdAndTitle(UUID workspaceId, String title);
    List<Assignment> findByWorkspaceId(UUID workspaceId);
    List<Assignment> findByCreatedById(UUID createdById);
    Assignment save(Assignment assignment);
    boolean existsByWorkspaceIdAndTitle(UUID workspaceId, String title);
    void deleteById(UUID id);
}
