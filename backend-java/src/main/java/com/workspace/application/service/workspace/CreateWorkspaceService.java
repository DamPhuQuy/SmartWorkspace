package com.workspace.application.service.workspace;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.workspace.CreateWorkspaceUseCase;
import com.workspace.application.port.out.user.UserRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.user.User;
import com.workspace.domain.model.workspace.WorkSpace;
import com.workspace.domain.model.workspace.WorkSpaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateWorkspaceService implements CreateWorkspaceUseCase {

    private final WorkSpaceRepositoryPort workSpaceRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final WorkSpaceMemberRepositoryPort workSpaceMemberRepositoryPort;

    @Override
    @Transactional
    public WorkSpace createWorkspace(Command command) {
        User owner = userRepositoryPort.findById(command.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner with ID " + command.ownerId() + " not found"));

        if (workSpaceRepositoryPort.existsByName(command.name())) {
            throw new DomainException("Workspace with name " + command.name() + " already exists");
        }
        if (workSpaceRepositoryPort.existsBySlug(command.slug())) {
            throw new DomainException("Workspace with slug " + command.slug() + " already exists");
        }

        UUID workspaceId = UUID.randomUUID();

        WorkSpace workSpace = WorkSpace.builder()
                .id(workspaceId)
                .name(command.name())
                .slug(command.slug())
                .description(command.description())
                .owner(owner)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        WorkSpace savedWorkspace = workSpaceRepositoryPort.save(workSpace);

        // Owner becomes the first member of the workspace
        WorkSpaceMember ownerMember = WorkSpaceMember.builder()
                .id(UUID.randomUUID())
                .workspace(savedWorkspace)
                .user(owner)
                .joinedAt(Instant.now())
                .build();

        workSpaceMemberRepositoryPort.save(ownerMember);

        return savedWorkspace;
    }
}
