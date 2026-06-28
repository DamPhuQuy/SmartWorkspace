package com.workspace.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.assignment.Submission;

public interface SubmissionRepositoryPort {
    Optional<Submission> findById(UUID id);
    List<Submission> findByAssignmentAssigneeId(UUID assignmentAssigneeId);
    Submission save(Submission submission);
    void deleteById(UUID id);
}
