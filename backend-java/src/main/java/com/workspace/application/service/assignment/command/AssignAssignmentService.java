package com.workspace.application.service.assignment.command;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.assignment.command.AssignAssignmentUseCase;
import com.workspace.application.port.out.assignment.AssignmentRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.assignment.Assignee;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.workspace.WorkspaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignAssignmentService implements AssignAssignmentUseCase {

    private final AssignmentRepositoryPort assignmentRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;
        
    @Override
    @Transactional
    public Assignee assignAssignment(Command command) {
        Assignment assignment = assignmentRepositoryPort.findById(command.assignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignment with ID " + command.assignmentId() + " not found"));

        WorkspaceMember member = workspaceRepositoryPort.findMemberById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        if (!assignment.getWorkspace().getId().equals(member.getWorkspace().getId())) {
            throw new DomainException("Workspace member must belong to the same workspace as the assignment");
        }

        if (assignmentRepositoryPort.existsAssigneeByAssignmentIdAndWorkspaceMemberId(command.assignmentId(), command.workspaceMemberId())) {
            throw new DomainException("Member is already assigned to this assignment");
        }

        Assignee assignee = Assignee.builder()
                .id(UUID.randomUUID())
                .assignment(assignment)
                .workspaceMember(member)
                .assignedAt(Instant.now())
                .build();

        return assignmentRepositoryPort.saveAssignee(assignee);
    }
}
