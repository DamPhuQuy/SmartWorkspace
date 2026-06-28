package com.workspace.application.service.assignment;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.assignment.CreateAssignmentUseCase;
import com.workspace.application.port.out.assignment.AssignmentRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.workspace.WorkSpace;
import com.workspace.domain.model.workspace.WorkSpaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateAssignmentService implements CreateAssignmentUseCase {

    private final AssignmentRepositoryPort assignmentRepositoryPort;
    private final WorkSpaceRepositoryPort workSpaceRepositoryPort;
    private final WorkSpaceMemberRepositoryPort workSpaceMemberRepositoryPort;

    @Override
    @Transactional
    public Assignment createAssignment(Command command) {
        WorkSpace workspace = workSpaceRepositoryPort.findById(command.workspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.workspaceId() + " not found"));

        WorkSpaceMember creator = workSpaceMemberRepositoryPort.findById(command.createdById())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.createdById() + " not found"));

        if (!creator.getWorkspace().getId().equals(command.workspaceId())) {
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
