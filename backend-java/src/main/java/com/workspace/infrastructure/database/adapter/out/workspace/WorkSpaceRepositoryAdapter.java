package com.workspace.infrastructure.database.adapter.out.workspace;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.workspace.WorkSpaceRepositoryPort;
import com.workspace.domain.model.workspace.WorkSpace;
import com.workspace.infrastructure.database.entity.workspace.WorkSpaceEntity;
import com.workspace.infrastructure.database.mapper.workspace.WorkSpaceMapper;
import com.workspace.infrastructure.database.repository.workspace.WorkSpaceJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WorkSpaceRepositoryAdapter implements WorkSpaceRepositoryPort {

    private final WorkSpaceJpaRepository workSpaceJpaRepository;

    @Override
    public Optional<WorkSpace> findById(UUID id) {
        return workSpaceJpaRepository.findById(id)
                .map(WorkSpaceMapper::toDomain);
    }

    @Override
    public Optional<WorkSpace> findBySlug(String slug) {
        return workSpaceJpaRepository.findBySlug(slug)
                .map(WorkSpaceMapper::toDomain);
    }

    @Override
    public Optional<WorkSpace> findByName(String name) {
        return workSpaceJpaRepository.findByName(name)
                .map(WorkSpaceMapper::toDomain);
    }

    @Override
    public WorkSpace save(WorkSpace workSpace) {
        WorkSpaceEntity entity = WorkSpaceMapper.toEntity(workSpace);
        WorkSpaceEntity savedEntity = workSpaceJpaRepository.save(entity);
        return WorkSpaceMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return workSpaceJpaRepository.existsBySlug(slug);
    }

    @Override
    public boolean existsByName(String name) {
        return workSpaceJpaRepository.existsByName(name);
    }

    @Override
    public void deleteById(UUID id) {
        workSpaceJpaRepository.deleteById(id);
    }
}
