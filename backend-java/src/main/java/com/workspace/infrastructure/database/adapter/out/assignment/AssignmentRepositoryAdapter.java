package com.workspace.infrastructure.database.adapter.out.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.assignment.AssignmentRepositoryPort;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.infrastructure.database.entity.assignment.AssignmentEntity;
import com.workspace.infrastructure.database.mapper.assignment.AssignmentMapper;
import com.workspace.infrastructure.database.repository.assignment.AssignmentJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AssignmentRepositoryAdapter implements AssignmentRepositoryPort {

    private final AssignmentJpaRepository assignmentJpaRepository;

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
                .collect(Collectors.toList());
    }

    @Override
    public List<Assignment> findByCreatedById(UUID createdById) {
        return assignmentJpaRepository.findByCreatedById(createdById)
                .stream()
                .map(AssignmentMapper::toDomain)
                .collect(Collectors.toList());
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
}
