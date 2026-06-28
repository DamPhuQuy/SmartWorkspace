package com.workspace.application.service.workspace;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.workspace.UpdateWorkspaceUseCase;
import com.workspace.application.port.out.workspace.WorkSpaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.workspace.WorkSpace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateWorkspaceService implements UpdateWorkspaceUseCase {

    private final WorkSpaceRepositoryPort workSpaceRepositoryPort;

    @Override
    @Transactional
    public WorkSpace updateWorkspace(Command command) {
        WorkSpace existingWorkspace = workSpaceRepositoryPort.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.id() + " not found"));

        if (command.name() != null && !command.name().equals(existingWorkspace.getName()) &&
                workSpaceRepositoryPort.existsByName(command.name())) {
            throw new DomainException("Workspace with name " + command.name() + " already exists");
        }

        if (command.slug() != null && !command.slug().equals(existingWorkspace.getSlug()) &&
                workSpaceRepositoryPort.existsBySlug(command.slug())) {
            throw new DomainException("Workspace with slug " + command.slug() + " already exists");
        }

        WorkSpace updatedWorkspace = WorkSpace.builder()
                .id(existingWorkspace.getId())
                .name(command.name() != null ? command.name() : existingWorkspace.getName())
                .slug(command.slug() != null ? command.slug() : existingWorkspace.getSlug())
                .description(command.description() != null ? command.description() : existingWorkspace.getDescription())
                .owner(existingWorkspace.getOwner())
                .createdAt(existingWorkspace.getCreatedAt())
                .updatedAt(Instant.now())
                .build();

        return workSpaceRepositoryPort.save(updatedWorkspace);
    }
}
