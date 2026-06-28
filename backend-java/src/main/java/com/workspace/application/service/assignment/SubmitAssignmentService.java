package com.workspace.application.service.assignment;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.assignment.SubmitAssignmentUseCase;
import com.workspace.application.port.out.assignment.AssignmentRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.assignment.Assignee;
import com.workspace.domain.model.assignment.Submission;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmitAssignmentService implements SubmitAssignmentUseCase {

        private final AssignmentRepositoryPort assignmentRepositoryPort;

    @Override
    @Transactional
    public Submission submitAssignment(Command command) {
        Assignee assignee = assignmentRepositoryPort.findAssigneeById(command.assignmentAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignee connection with ID " + command.assignmentAssigneeId() + " not found"));

        Submission submission = Submission.builder()
                .id(UUID.randomUUID())
                .assignmentAssignee(assignee)
                .submissionUrl(command.submissionUrl())
                .submittedAt(Instant.now())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return assignmentRepositoryPort.saveSubmission(submission);
    }
}
