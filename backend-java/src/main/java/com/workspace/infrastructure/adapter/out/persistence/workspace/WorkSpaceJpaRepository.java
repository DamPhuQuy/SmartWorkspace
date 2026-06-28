package com.workspace.infrastructure.adapter.out.persistence.workspace;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSpaceJpaRepository extends JpaRepository<WorkSpaceEntity, UUID> {
    Optional<WorkSpaceEntity> findBySlug(String slug);
    Optional<WorkSpaceEntity> findByName(String name);
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
}
