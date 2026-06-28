package com.workspace.adapter.out.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.user.RoleRepositoryPort;
import com.workspace.domain.model.user.Role;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleJpaRepository;

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
                .collect(Collectors.toList());
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
}
