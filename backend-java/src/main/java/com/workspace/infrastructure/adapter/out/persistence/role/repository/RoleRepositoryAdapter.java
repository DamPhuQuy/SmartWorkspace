package com.workspace.infrastructure.adapter.out.persistence.role.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.role.RoleRepositoryPort;
import com.workspace.domain.model.role.Permission;
import com.workspace.domain.model.role.Role;
import com.workspace.infrastructure.adapter.out.persistence.role.entity.PermissionEntity;
import com.workspace.infrastructure.adapter.out.persistence.role.entity.RoleEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleJpaRepository;
    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public Optional<Role> findById(UUID id) {
        return roleJpaRepository.findById(id)
                .map(RoleMapper::toDomain);
    }

    @Override
    public Optional<Role> findByWorkspaceIdAndName(UUID workspaceId, String name) {
        return roleJpaRepository.findByWorkspaceIdAndName(workspaceId, name)
                .map(RoleMapper::toDomain);
    }

    @Override
    public List<Role> findByWorkspaceId(UUID workspaceId) {
        return roleJpaRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(RoleMapper::toDomain)
                .toList();
    }

    @Override
    public Role save(Role role) {
        RoleEntity entity = RoleMapper.toEntity(role);
        RoleEntity savedEntity = roleJpaRepository.save(entity);
        return RoleMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByWorkspaceIdAndName(UUID workspaceId, String name) {
        return roleJpaRepository.existsByWorkspaceIdAndName(workspaceId, name);
    }

    @Override
    public void deleteById(UUID id) {
        roleJpaRepository.deleteById(id);
    }

    // Permission methods
    @Override
    public Optional<Permission> findPermissionById(UUID id) {
        return permissionJpaRepository.findById(id)
                .map(PermissionMapper::toDomain);
    }

    @Override
    public Optional<Permission> findPermissionByName(String name) {
        return permissionJpaRepository.findByCode(name)
                .map(PermissionMapper::toDomain);
    }

    @Override
    public Permission savePermission(Permission permission) {
        PermissionEntity entity = PermissionMapper.toEntity(permission);
        PermissionEntity savedEntity = permissionJpaRepository.save(entity);
        return PermissionMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsPermissionByName(String name) {
        return permissionJpaRepository.existsByCode(name);
    }

    @Override
    public void deletePermissionById(UUID id) {
        permissionJpaRepository.deleteById(id);
    }
}
