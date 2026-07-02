package com.workspace.application.service.assignment.command;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.assignment.command.CreateAssignmentUseCase;
import com.workspace.application.port.out.assignment.AssignmentRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.workspace.Workspace;
import com.workspace.domain.model.workspace.WorkspaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateAssignmentService implements CreateAssignmentUseCase {

    private final AssignmentRepositoryPort assignmentRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;
    
    @Override
    @Transactional
    public Assignment createAssignment(Command command) {
        Workspace workspace = workspaceRepositoryPort.findById(command.workspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.workspaceId() + " not found"));

        WorkspaceMember creator = workspaceRepositoryPort.findMemberById(command.createdById())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.createdById() + " not found"));

        if (!creator.getWorkspaceId().equals(command.workspaceId())) {
            throw new DomainException("Creator must belong to the same workspace");
        }

        if (command.deadline().isBefore(Instant.now())) {
            throw new DomainException("Assignment deadline must be in the future");
        }

        if (assignmentRepositoryPort.existsByWorkspaceIdAndTitle(command.workspaceId(), command.title())) {
            throw new DomainException("Assignment with title '" + command.title() + "' already exists in this workspace");
        }

        Assignment assignment = Assignment.builder()
                .id(UUID.randomUUID())
                .workspace(workspace)
                .title(command.title())
                .description(command.description())
                .deadline(command.deadline())
                .createdBy(creator)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return assignmentRepositoryPort.save(assignment);
    }
}
