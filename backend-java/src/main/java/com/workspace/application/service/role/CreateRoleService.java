package com.workspace.application.service.role;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.role.CreateRoleUseCase;
import com.workspace.application.port.out.user.RoleRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.user.Role;
import com.workspace.domain.model.workspace.WorkSpace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateRoleService implements CreateRoleUseCase {

    private final RoleRepositoryPort roleRepositoryPort;
    private final WorkSpaceRepositoryPort workSpaceRepositoryPort;

    @Override
    @Transactional
    public Role createRole(Command command) {
        WorkSpace workspace = workSpaceRepositoryPort.findById(command.workspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.workspaceId() + " not found"));

        if (roleRepositoryPort.existsByWorkspaceIdAndName(command.workspaceId(), command.name())) {
            throw new DomainException("Role with name " + command.name() + " already exists in this workspace");
        }

        Role role = Role.builder()
                .id(UUID.randomUUID())
                .workspace(workspace)
                .name(command.name())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return roleRepositoryPort.save(role);
    }
}
