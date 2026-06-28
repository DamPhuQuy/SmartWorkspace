package com.workspace.infrastructure.database.adapter.out.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.workspace.application.port.out.SubmissionRepositoryPort;
import com.workspace.domain.model.assignment.Submission;
import com.workspace.infrastructure.database.entity.assignment.SubmissionEntity;
import com.workspace.infrastructure.database.mapper.assignment.SubmissionMapper;
import com.workspace.infrastructure.database.repository.assignment.SubmissionJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubmissionRepositoryAdapter implements SubmissionRepositoryPort {

    private final SubmissionJpaRepository submissionJpaRepository;

    @Override
    public Optional<Submission> findById(UUID id) {
        return submissionJpaRepository.findById(id)
                .map(SubmissionMapper::toDomain);
    }

    @Override
    public List<Submission> findByAssignmentAssigneeId(UUID assignmentAssigneeId) {
        return submissionJpaRepository.findByAssignmentAssigneeId(assignmentAssigneeId)
                .stream()
                .map(SubmissionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Submission save(Submission submission) {
        SubmissionEntity entity = SubmissionMapper.toEntity(submission);
        SubmissionEntity savedEntity = submissionJpaRepository.save(entity);
        return SubmissionMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        submissionJpaRepository.deleteById(id);
    }
}
