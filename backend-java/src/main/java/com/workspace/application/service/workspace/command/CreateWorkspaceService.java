package com.workspace.application.service.workspace.command;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.workspace.command.CreateWorkspaceUseCase;
import com.workspace.application.port.out.user.UserRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.user.User;
import com.workspace.domain.model.workspace.Workspace;
import com.workspace.domain.model.workspace.WorkspaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateWorkspaceService implements CreateWorkspaceUseCase {

    private final WorkspaceRepositoryPort workspaceRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    
    @Override
    @Transactional
    public Workspace createWorkspace(Command command) {
        User owner = userRepositoryPort.findById(command.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner with ID " + command.ownerId() + " not found"));

        if (workspaceRepositoryPort.existsByName(command.name())) {
            throw new DomainException("Workspace with name " + command.name() + " already exists");
        }
        if (workspaceRepositoryPort.existsBySlug(command.slug())) {
            throw new DomainException("Workspace with slug " + command.slug() + " already exists");
        }

        UUID workspaceId = UUID.randomUUID();

        Workspace workspace = Workspace.builder()
                .id(workspaceId)
                .name(command.name())
                .slug(command.slug())
                .description(command.description())
                .ownerId(owner.getId())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Workspace savedWorkspace = workspaceRepositoryPort.save(workspace);

        // Owner becomes the first member of the workspace
        WorkspaceMember ownerMember = WorkspaceMember.join(savedWorkspace.getId(), owner.getId());

        workspaceRepositoryPort.saveMember(ownerMember);

        return savedWorkspace;
    }
}
