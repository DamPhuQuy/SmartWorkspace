package com.workspace.infrastructure.adapter.in.web.mapper;

import java.util.UUID;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.assignment.Assignee;
import com.workspace.domain.model.assignment.Submission;
import com.workspace.infrastructure.adapter.in.web.dto.AssignmentDto;

public class AssignmentWebMapper {

    private AssignmentWebMapper() {}

    public static AssignmentDto.AssignmentResponse toResponse(Assignment assignment) {
        if (assignment == null) {
            return null;
        }

        UUID workspaceId = assignment.getWorkspace() != null ? assignment.getWorkspace().getId() : null;

        return new AssignmentDto.AssignmentResponse(
            assignment.getId(),
            workspaceId,
            assignment.getTitle(),
            assignment.getDescription(),
            assignment.getDeadline(),
            WorkspaceWebMapper.toMemberResponse(assignment.getCreatedBy()),
            assignment.getCreatedAt(),
            assignment.getUpdatedAt()
        );
    }

    public static AssignmentDto.AssigneeResponse toAssigneeResponse(Assignee assignee) {
        if (assignee == null) {
            return null;
        }

        UUID assignmentId = assignee.getAssignment() != null ? assignee.getAssignment().getId() : null;

        return new AssignmentDto.AssigneeResponse(
            assignee.getId(),
            assignmentId,
            WorkspaceWebMapper.toMemberResponse(assignee.getWorkspaceMember()),
            assignee.getAssignedAt()
        );
    }

    public static AssignmentDto.SubmissionResponse toSubmissionResponse(Submission submission) {
        if (submission == null) {
            return null;
        }

        return new AssignmentDto.SubmissionResponse(
            submission.getId(),
            toAssigneeResponse(submission.getAssignmentAssignee()),
            submission.getSubmissionUrl(),
            submission.getSubmittedAt(),
            submission.getCreatedAt(),
            submission.getUpdatedAt()
        );
    }
}
