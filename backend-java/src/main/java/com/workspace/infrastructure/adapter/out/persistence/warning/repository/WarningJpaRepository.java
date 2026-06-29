package com.workspace.infrastructure.adapter.out.persistence.warning.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.warning.entity.WarningEntity;

public interface WarningJpaRepository extends JpaRepository<WarningEntity, UUID> {
    List<WarningEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
}
