package com.workspace.application.port.out.workspace;

import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpace;

public interface WorkSpaceRepositoryPort {
    Optional<WorkSpace> findById(UUID id);
    Optional<WorkSpace> findBySlug(String slug);
    Optional<WorkSpace> findByName(String name);
    WorkSpace save(WorkSpace workSpace);
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
    void deleteById(UUID id);
}
