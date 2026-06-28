package com.workspace.infrastructure.adapter.out.persistence.team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.team.TeamRepositoryPort;
import com.workspace.domain.model.team.Team;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryAdapter implements TeamRepositoryPort {

    private final TeamJpaRepository teamJpaRepository;

    @Override
    public Optional<Team> findById(UUID id) {
        return teamJpaRepository.findById(id)
                .map(TeamMapper::toDomain);
    }

    @Override
    public Optional<Team> findByWorkspaceIdAndName(UUID workspaceId, String name) {
        return teamJpaRepository.findByWorkspaceIdAndName(workspaceId, name)
                .map(TeamMapper::toDomain);
    }

    @Override
    public List<Team> findByWorkspaceId(UUID workspaceId) {
        return teamJpaRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(TeamMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Team save(Team team) {
        TeamEntity entity = TeamMapper.toEntity(team);
        TeamEntity savedEntity = teamJpaRepository.save(entity);
        return TeamMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByWorkspaceIdAndName(UUID workspaceId, String name) {
        return teamJpaRepository.existsByWorkspaceIdAndName(workspaceId, name);
    }

    @Override
    public void deleteById(UUID id) {
        teamJpaRepository.deleteById(id);
    }
}
