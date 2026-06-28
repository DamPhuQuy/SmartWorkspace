package com.workspace.infrastructure.adapter.out.persistence.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.workspace.application.port.out.assignment.AssignmentRepositoryPort;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.assignment.Assignee;
import com.workspace.domain.model.assignment.Submission;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AssignmentRepositoryAdapter implements AssignmentRepositoryPort {

    private final AssignmentJpaRepository assignmentJpaRepository;
    private final AssigneeJpaRepository assigneeJpaRepository;
    private final SubmissionJpaRepository submissionJpaRepository;

    @Override
    public Optional<Assignment> findById(UUID id) {
        return assignmentJpaRepository.findById(id)
                .map(AssignmentMapper::toDomain);
    }

    @Override
    public Optional<Assignment> findByWorkspaceIdAndTitle(UUID workspaceId, String title) {
        return assignmentJpaRepository.findByWorkspaceIdAndTitle(workspaceId, title)
                .map(AssignmentMapper::toDomain);
    }

    @Override
    public List<Assignment> findByWorkspaceId(UUID workspaceId) {
        return assignmentJpaRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(AssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public List<Assignment> findByCreatedById(UUID createdById) {
        return assignmentJpaRepository.findByCreatedById(createdById)
                .stream()
                .map(AssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public Assignment save(Assignment assignment) {
        AssignmentEntity entity = AssignmentMapper.toEntity(assignment);
        AssignmentEntity savedEntity = assignmentJpaRepository.save(entity);
        return AssignmentMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByWorkspaceIdAndTitle(UUID workspaceId, String title) {
        return assignmentJpaRepository.existsByWorkspaceIdAndTitle(workspaceId, title);
    }

    @Override
    public void deleteById(UUID id) {
        assignmentJpaRepository.deleteById(id);
    }

    // Assignee methods
    @Override
    public Optional<Assignee> findAssigneeById(UUID id) {
        return assigneeJpaRepository.findById(id)
                .map(AssigneeMapper::toDomain);
    }

    @Override
    public Optional<Assignee> findAssigneeByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId) {
        return assigneeJpaRepository.findByAssignmentIdAndWorkspaceMemberId(assignmentId, workspaceMemberId)
                .map(AssigneeMapper::toDomain);
    }

    @Override
    public List<Assignee> findAssigneesByAssignmentId(UUID assignmentId) {
        return assigneeJpaRepository.findByAssignmentId(assignmentId)
                .stream()
                .map(AssigneeMapper::toDomain)
                .toList();
    }

    @Override
    public List<Assignee> findAssigneesByWorkspaceMemberId(UUID workspaceMemberId) {
        return assigneeJpaRepository.findByWorkspaceMemberId(workspaceMemberId)
                .stream()
                .map(AssigneeMapper::toDomain)
                .toList();
    }

    @Override
    public Assignee saveAssignee(Assignee assignee) {
        AssigneeEntity entity = AssigneeMapper.toEntity(assignee);
        AssigneeEntity savedEntity = assigneeJpaRepository.save(entity);
        return AssigneeMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsAssigneeByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId) {
        return assigneeJpaRepository.existsByAssignmentIdAndWorkspaceMemberId(assignmentId, workspaceMemberId);
    }

    @Override
    public void deleteAssigneeById(UUID id) {
        assigneeJpaRepository.deleteById(id);
    }

    // Submission methods
    @Override
    public Optional<Submission> findSubmissionById(UUID id) {
        return submissionJpaRepository.findById(id)
                .map(SubmissionMapper::toDomain);
    }

    @Override
    public List<Submission> findSubmissionsByAssignmentAssigneeId(UUID assignmentAssigneeId) {
        return submissionJpaRepository.findByAssignmentAssigneeId(assignmentAssigneeId)
                .stream()
                .map(SubmissionMapper::toDomain)
                .toList();
    }

    @Override
    public Submission saveSubmission(Submission submission) {
        SubmissionEntity entity = SubmissionMapper.toEntity(submission);
        SubmissionEntity savedEntity = submissionJpaRepository.save(entity);
        return SubmissionMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteSubmissionById(UUID id) {
        submissionJpaRepository.deleteById(id);
    }
}
