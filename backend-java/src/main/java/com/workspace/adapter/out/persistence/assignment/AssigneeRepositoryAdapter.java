package com.workspace.adapter.out.persistence.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.assignment.AssigneeRepositoryPort;
import com.workspace.domain.model.assignment.Assignee;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AssigneeRepositoryAdapter implements AssigneeRepositoryPort {

    private final AssigneeJpaRepository assigneeJpaRepository;

    @Override
    public Optional<Assignee> findById(UUID id) {
        return assigneeJpaRepository.findById(id)
                .map(AssigneeMapper::toDomain);
    }

    @Override
    public Optional<Assignee> findByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId) {
        return assigneeJpaRepository.findByAssignmentIdAndWorkspaceMemberId(assignmentId, workspaceMemberId)
                .map(AssigneeMapper::toDomain);
    }

    @Override
    public List<Assignee> findByAssignmentId(UUID assignmentId) {
        return assigneeJpaRepository.findByAssignmentId(assignmentId)
                .stream()
                .map(AssigneeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Assignee> findByWorkspaceMemberId(UUID workspaceMemberId) {
        return assigneeJpaRepository.findByWorkspaceMemberId(workspaceMemberId)
                .stream()
                .map(AssigneeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Assignee save(Assignee assignee) {
        AssigneeEntity entity = AssigneeMapper.toEntity(assignee);
        AssigneeEntity savedEntity = assigneeJpaRepository.save(entity);
        return AssigneeMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByAssignmentIdAndWorkspaceMemberId(UUID assignmentId, UUID workspaceMemberId) {
        return assigneeJpaRepository.existsByAssignmentIdAndWorkspaceMemberId(assignmentId, workspaceMemberId);
    }

    @Override
    public void deleteById(UUID id) {
        assigneeJpaRepository.deleteById(id);
    }
}
