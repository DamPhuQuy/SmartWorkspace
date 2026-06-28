package com.workspace.infrastructure.adapter.out.persistence.workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.model.workspace.Workspace;
import com.workspace.domain.model.workspace.WorkspaceMember;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WorkspaceRepositoryAdapter implements WorkspaceRepositoryPort {

    private final WorkspaceJpaRepository workspaceJpaRepository;
    private final WorkspaceMemberJpaRepository workspaceMemberJpaRepository;
    private final WorkspaceMemberRoleJpaRepository workspaceMemberRoleJpaRepository;

    @Override
    public Optional<Workspace> findById(UUID id) {
        return workspaceJpaRepository.findById(id)
                .map(WorkspaceMapper::toDomain);
    }

    @Override
    public Optional<Workspace> findBySlug(String slug) {
        return workspaceJpaRepository.findBySlug(slug)
                .map(WorkspaceMapper::toDomain);
    }

    @Override
    public Optional<Workspace> findByName(String name) {
        return workspaceJpaRepository.findByName(name)
                .map(WorkspaceMapper::toDomain);
    }

    @Override
    public Workspace save(Workspace workspace) {
        WorkspaceEntity entity = WorkspaceMapper.toEntity(workspace);
        WorkspaceEntity savedEntity = workspaceJpaRepository.save(entity);
        return WorkspaceMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return workspaceJpaRepository.existsBySlug(slug);
    }

    @Override
    public boolean existsByName(String name) {
        return workspaceJpaRepository.existsByName(name);
    }

    @Override
    public void deleteById(UUID id) {
        workspaceJpaRepository.deleteById(id);
    }

    // WorkspaceMember methods
    @Override
    public Optional<WorkspaceMember> findMemberById(UUID id) {
        return workspaceMemberJpaRepository.findById(id)
                .map(WorkspaceMemberMapper::toDomain);
    }

    @Override
    public Optional<WorkspaceMember> findMemberByWorkspaceIdAndUserId(UUID workspaceId, UUID userId) {
        return workspaceMemberJpaRepository.findByWorkspaceIdAndUserId(workspaceId, userId)
                .map(WorkspaceMemberMapper::toDomain);
    }

    @Override
    public List<WorkspaceMember> findMembersByWorkspaceId(UUID workspaceId) {
        return workspaceMemberJpaRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(WorkspaceMemberMapper::toDomain)
                .toList();
    }

    @Override
    public List<WorkspaceMember> findMembersByUserId(UUID userId) {
        return workspaceMemberJpaRepository.findByUserId(userId)
                .stream()
                .map(WorkspaceMemberMapper::toDomain)
                .toList();
    }

    @Override
    public WorkspaceMember saveMember(WorkspaceMember workspaceMember) {
        WorkspaceMemberEntity entity = WorkspaceMemberMapper.toEntity(workspaceMember);
        WorkspaceMemberEntity savedEntity = workspaceMemberJpaRepository.save(entity);
        return WorkspaceMemberMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsMemberByWorkspaceIdAndUserId(UUID workspaceId, UUID userId) {
        return workspaceMemberJpaRepository.existsByWorkspaceIdAndUserId(workspaceId, userId);
    }

    @Override
    public void deleteMemberById(UUID id) {
        workspaceMemberJpaRepository.deleteById(id);
    }

    // WorkspaceMemberRole methods
    @Override
    public WorkspaceMemberRole saveMemberRole(WorkspaceMemberRole workspaceMemberRole) {
        WorkspaceMemberRoleEntity entity = WorkspaceMemberRoleMapper.toEntity(workspaceMemberRole);
        WorkspaceMemberRoleEntity savedEntity = workspaceMemberRoleJpaRepository.save(entity);
        return WorkspaceMemberRoleMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<WorkspaceMemberRole> findMemberRoleByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId) {
        return workspaceMemberRoleJpaRepository.findByWorkspaceMemberIdAndRoleId(workspaceMemberId, roleId)
                .map(WorkspaceMemberRoleMapper::toDomain);
    }

    @Override
    public List<WorkspaceMemberRole> findMemberRolesByWorkspaceMemberId(UUID workspaceMemberId) {
        return workspaceMemberRoleJpaRepository.findByWorkspaceMemberId(workspaceMemberId)
                .stream()
                .map(WorkspaceMemberRoleMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteMemberRoleById(UUID id) {
        workspaceMemberRoleJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsMemberRoleByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId) {
        return workspaceMemberRoleJpaRepository.existsByWorkspaceMemberIdAndRoleId(workspaceMemberId, roleId);
    }
}
