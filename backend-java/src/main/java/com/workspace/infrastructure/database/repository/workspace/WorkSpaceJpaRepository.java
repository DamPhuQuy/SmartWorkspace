package com.workspace.infrastructure.database.repository.workspace;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.workspace.WorkSpaceEntity;

public interface WorkSpaceJpaRepository extends JpaRepository<WorkSpaceEntity, UUID> {
    Optional<WorkSpaceEntity> findBySlug(String slug);
    Optional<WorkSpaceEntity> findByName(String name);
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
}
