package com.workspace.application.port.in.assignment.command;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.assignment.Assignment;

public interface CreateAssignmentUseCase {
    Assignment createAssignment(Command command);

    record Command(
        UUID workspaceId,
        String title,
        String description,
        Instant deadline,
        UUID createdById
    ) {}
}
