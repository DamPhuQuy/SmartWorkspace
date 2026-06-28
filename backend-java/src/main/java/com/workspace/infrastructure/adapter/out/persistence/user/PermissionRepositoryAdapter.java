package com.workspace.infrastructure.adapter.out.persistence.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.user.PermissionRepositoryPort;
import com.workspace.domain.model.user.Permission;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryAdapter implements PermissionRepositoryPort {

    private final PermissionJpaRepository permissionJpaRepository;

    @Override
    public Optional<Permission> findById(UUID id) {
        return permissionJpaRepository.findById(id)
                .map(PermissionMapper::toDomain);
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return permissionJpaRepository.findByName(name)
                .map(PermissionMapper::toDomain);
    }

    @Override
    public Permission save(Permission permission) {
        PermissionEntity entity = PermissionMapper.toEntity(permission);
        PermissionEntity savedEntity = permissionJpaRepository.save(entity);
        return PermissionMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return permissionJpaRepository.existsByName(name);
    }

    @Override
    public void deleteById(UUID id) {
        permissionJpaRepository.deleteById(id);
    }
}
