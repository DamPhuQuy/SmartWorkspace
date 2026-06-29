package com.workspace.application.service.role.command;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.role.command.DeleteRoleUseCase;
import com.workspace.application.port.out.role.RoleRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteRoleService implements DeleteRoleUseCase {

    private final RoleRepositoryPort roleRepositoryPort;

    @Override
    @Transactional
    public void deleteRole(UUID id) {
        if (!roleRepositoryPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Role with ID " + id + " not found");
        }
        roleRepositoryPort.deleteById(id);
    }
}
