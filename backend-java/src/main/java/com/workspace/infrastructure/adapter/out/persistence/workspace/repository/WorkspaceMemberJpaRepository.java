package com.workspace.infrastructure.adapter.out.persistence.workspace.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceMemberEntity;

public interface WorkspaceMemberJpaRepository extends JpaRepository<WorkspaceMemberEntity, UUID> {
    Optional<WorkspaceMemberEntity> findByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
    List<WorkspaceMemberEntity> findByWorkspaceId(UUID workspaceId);
    List<WorkspaceMemberEntity> findByUserId(UUID userId);
    boolean existsByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
}
