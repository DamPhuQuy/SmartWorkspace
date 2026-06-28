package com.workspace.adapter.out.persistence.workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.domain.model.workspace.WorkSpaceMember;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WorkSpaceMemberRepositoryAdapter implements WorkSpaceMemberRepositoryPort {

    private final WorkSpaceMemberJpaRepository workSpaceMemberJpaRepository;

    @Override
    public Optional<WorkSpaceMember> findById(UUID id) {
        return workSpaceMemberJpaRepository.findById(id)
                .map(WorkSpaceMemberMapper::toDomain);
    }

    @Override
    public Optional<WorkSpaceMember> findByWorkspaceIdAndUserId(UUID workspaceId, UUID userId) {
        return workSpaceMemberJpaRepository.findByWorkspaceIdAndUserId(workspaceId, userId)
                .map(WorkSpaceMemberMapper::toDomain);
    }

    @Override
    public List<WorkSpaceMember> findByWorkspaceId(UUID workspaceId) {
        return workSpaceMemberJpaRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(WorkSpaceMemberMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkSpaceMember> findByUserId(UUID userId) {
        return workSpaceMemberJpaRepository.findByUserId(userId)
                .stream()
                .map(WorkSpaceMemberMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public WorkSpaceMember save(WorkSpaceMember workSpaceMember) {
        WorkSpaceMemberEntity entity = WorkSpaceMemberMapper.toEntity(workSpaceMember);
        WorkSpaceMemberEntity savedEntity = workSpaceMemberJpaRepository.save(entity);
        return WorkSpaceMemberMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByWorkspaceIdAndUserId(UUID workspaceId, UUID userId) {
        return workSpaceMemberJpaRepository.existsByWorkspaceIdAndUserId(workspaceId, userId);
    }

    @Override
    public void deleteById(UUID id) {
        workSpaceMemberJpaRepository.deleteById(id);
    }
}
