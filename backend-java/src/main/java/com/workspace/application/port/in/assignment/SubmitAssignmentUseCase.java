package com.workspace.application.port.in.assignment;

import java.util.UUID;
import com.workspace.domain.model.assignment.Submission;

public interface SubmitAssignmentUseCase {
    Submission submitAssignment(Command command);

    record Command(
        UUID assignmentAssigneeId,
        String submissionUrl
    ) {}
}
