package com.workspace.application.service.role.command;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.role.command.CreateRoleUseCase;
import com.workspace.application.port.out.role.RoleRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.role.Role;
import com.workspace.domain.model.workspace.Workspace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateRoleService implements CreateRoleUseCase {

    private final RoleRepositoryPort roleRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;

    @Override
    @Transactional
    public Role createRole(Command command) {
        Workspace workspace = workspaceRepositoryPort.findById(command.workspaceId())
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
