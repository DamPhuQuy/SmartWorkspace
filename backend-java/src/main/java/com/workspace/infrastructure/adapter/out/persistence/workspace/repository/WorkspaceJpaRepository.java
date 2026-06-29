package com.workspace.infrastructure.adapter.out.persistence.workspace.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceEntity;

public interface WorkspaceJpaRepository extends JpaRepository<WorkspaceEntity, UUID> {
    Optional<WorkspaceEntity> findBySlug(String slug);
    Optional<WorkspaceEntity> findByName(String name);
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
}
