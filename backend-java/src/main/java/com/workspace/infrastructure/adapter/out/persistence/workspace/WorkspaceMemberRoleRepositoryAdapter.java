package com.workspace.infrastructure.adapter.out.persistence.workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.workspace.WorkspaceMemberRoleRepositoryPort;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WorkspaceMemberRoleRepositoryAdapter implements WorkspaceMemberRoleRepositoryPort {

    private final WorkspaceMemberRoleJpaRepository workspaceMemberRoleJpaRepository;

    @Override
    public WorkspaceMemberRole save(WorkspaceMemberRole workspaceMemberRole) {
        WorkspaceMemberRoleEntity entity = WorkspaceMemberRoleMapper.toEntity(workspaceMemberRole);
        WorkspaceMemberRoleEntity savedEntity = workspaceMemberRoleJpaRepository.save(entity);
        return WorkspaceMemberRoleMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<WorkspaceMemberRole> findByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId) {
        return workspaceMemberRoleJpaRepository.findByWorkspaceMemberIdAndRoleId(workspaceMemberId, roleId)
                .map(WorkspaceMemberRoleMapper::toDomain);
    }

    @Override
    public List<WorkspaceMemberRole> findByWorkspaceMemberId(UUID workspaceMemberId) {
        return workspaceMemberRoleJpaRepository.findByWorkspaceMemberId(workspaceMemberId).stream()
                .map(WorkspaceMemberRoleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        workspaceMemberRoleJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId) {
        return workspaceMemberRoleJpaRepository.existsByWorkspaceMemberIdAndRoleId(workspaceMemberId, roleId);
    }
}
