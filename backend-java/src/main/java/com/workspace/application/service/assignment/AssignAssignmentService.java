package com.workspace.application.service.assignment;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.assignment.AssignAssignmentUseCase;
import com.workspace.application.port.out.assignment.AssigneeRepositoryPort;
import com.workspace.application.port.out.assignment.AssignmentRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.assignment.Assignee;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.workspace.WorkSpaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignAssignmentService implements AssignAssignmentUseCase {

    private final AssignmentRepositoryPort assignmentRepositoryPort;
    private final WorkSpaceMemberRepositoryPort workSpaceMemberRepositoryPort;
    private final AssigneeRepositoryPort assigneeRepositoryPort;

    @Override
    @Transactional
    public Assignee assignAssignment(Command command) {
        Assignment assignment = assignmentRepositoryPort.findById(command.assignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignment with ID " + command.assignmentId() + " not found"));

        WorkSpaceMember member = workSpaceMemberRepositoryPort.findById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        if (!assignment.getWorkspace().getId().equals(member.getWorkspace().getId())) {
            throw new DomainException("Workspace member must belong to the same workspace as the assignment");
        }

        if (assigneeRepositoryPort.existsByAssignmentIdAndWorkspaceMemberId(command.assignmentId(), command.workspaceMemberId())) {
            throw new DomainException("Member is already assigned to this assignment");
        }

        Assignee assignee = Assignee.builder()
                .id(UUID.randomUUID())
                .assignment(assignment)
                .workspaceMember(member)
                .assignedAt(Instant.now())
                .build();

        return assigneeRepositoryPort.save(assignee);
    }
}
