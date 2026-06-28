package com.workspace.adapter.out.persistence.warning;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.warning.WarningRepositoryPort;
import com.workspace.domain.model.warning.Warning;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WarningRepositoryAdapter implements WarningRepositoryPort {

    private final WarningJpaRepository warningJpaRepository;

    @Override
    public Optional<Warning> findById(UUID id) {
        return warningJpaRepository.findById(id)
                .map(WarningMapper::toDomain);
    }

    @Override
    public List<Warning> findByWorkspaceMemberId(UUID workspaceMemberId) {
        return warningJpaRepository.findByWorkspaceMemberId(workspaceMemberId)
                .stream()
                .map(WarningMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Warning save(Warning warning) {
        WarningEntity entity = WarningMapper.toEntity(warning);
        WarningEntity savedEntity = warningJpaRepository.save(entity);
        return WarningMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        warningJpaRepository.deleteById(id);
    }
}
