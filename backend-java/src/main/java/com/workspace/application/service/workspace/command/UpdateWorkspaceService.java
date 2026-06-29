package com.workspace.application.service.workspace.command;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.workspace.command.UpdateWorkspaceUseCase;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.workspace.Workspace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateWorkspaceService implements UpdateWorkspaceUseCase {

    private final WorkspaceRepositoryPort workspaceRepositoryPort;

    @Override
    @Transactional
    public Workspace updateWorkspace(Command command) {
        Workspace existingWorkspace = workspaceRepositoryPort.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.id() + " not found"));

        if (command.name() != null && !command.name().equals(existingWorkspace.getName()) &&
                workspaceRepositoryPort.existsByName(command.name())) {
            throw new DomainException("Workspace with name " + command.name() + " already exists");
        }

        if (command.slug() != null && !command.slug().equals(existingWorkspace.getSlug()) &&
                workspaceRepositoryPort.existsBySlug(command.slug())) {
            throw new DomainException("Workspace with slug " + command.slug() + " already exists");
        }

        Workspace updatedWorkspace = Workspace.builder()
                .id(existingWorkspace.getId())
                .name(command.name() != null ? command.name() : existingWorkspace.getName())
                .slug(command.slug() != null ? command.slug() : existingWorkspace.getSlug())
                .description(command.description() != null ? command.description() : existingWorkspace.getDescription())
                .owner(existingWorkspace.getOwner())
                .createdAt(existingWorkspace.getCreatedAt())
                .updatedAt(Instant.now())
                .build();

        return workspaceRepositoryPort.save(updatedWorkspace);
    }
}
