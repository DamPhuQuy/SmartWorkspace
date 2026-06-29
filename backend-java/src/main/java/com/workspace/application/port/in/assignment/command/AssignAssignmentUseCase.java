package com.workspace.application.port.in.assignment.command;

import java.util.UUID;
import com.workspace.domain.model.assignment.Assignee;

public interface AssignAssignmentUseCase {
    Assignee assignAssignment(Command command);

    record Command(
        UUID assignmentId,
        UUID workspaceMemberId
    ) {}
}
