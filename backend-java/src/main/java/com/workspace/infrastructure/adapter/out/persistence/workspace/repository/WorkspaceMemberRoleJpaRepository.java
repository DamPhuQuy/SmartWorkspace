package com.workspace.infrastructure.adapter.out.persistence.workspace.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceMemberRoleEntity;


public interface WorkspaceMemberRoleJpaRepository extends JpaRepository<WorkspaceMemberRoleEntity, UUID> {
    Optional<WorkspaceMemberRoleEntity> findByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId);
    List<WorkspaceMemberRoleEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
    boolean existsByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId);
}
