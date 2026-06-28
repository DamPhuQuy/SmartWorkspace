package com.workspace.application.port.out.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.assignment.Assignee;
import com.workspace.domain.model.assignment.Submission;

public interface AssignmentRepositoryPort {
    Optional<Assignment> findById(UUID id);
    Optional<Assignment> findByWorkspaceIdAndTitle(UUID workspaceId, String title);
    List<Assignment> findByWorkspaceId(UUID workspaceId);
    List<Assignment> findByCreatedById(UUID createdById);
    Assignment save(Assignment assignment);
    boolean existsByWorkspaceIdAndTitle(UUID workspaceId, String title);
    void deleteById(UUID id);

    // Assignee methods
    Optional<Assignee> findAssigneeById(UUID id);
    Optional<Assignee> findAssigneeByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId);
    List<Assignee> findAssigneesByAssignmentId(UUID assignmentId);
    List<Assignee> findAssigneesByWorkspaceMemberId(UUID workspaceMemberId);
    Assignee saveAssignee(Assignee assignee);
    boolean existsAssigneeByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId);
    void deleteAssigneeById(UUID id);

    // Submission methods
    Optional<Submission> findSubmissionById(UUID id);
    List<Submission> findSubmissionsByAssignmentAssigneeId(UUID assignmentAssigneeId);
    Submission saveSubmission(Submission submission);
    void deleteSubmissionById(UUID id);
}
