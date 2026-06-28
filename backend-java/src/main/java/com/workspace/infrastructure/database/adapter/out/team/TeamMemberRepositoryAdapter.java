package com.workspace.infrastructure.database.adapter.out.team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.workspace.application.port.out.TeamMemberRepositoryPort;
import com.workspace.domain.model.team.TeamMember;
import com.workspace.infrastructure.database.entity.team.TeamMemberEntity;
import com.workspace.infrastructure.database.mapper.team.TeamMemberMapper;
import com.workspace.infrastructure.database.repository.team.TeamMemberJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamMemberRepositoryAdapter implements TeamMemberRepositoryPort {

    private final TeamMemberJpaRepository teamMemberJpaRepository;

    @Override
    public Optional<TeamMember> findById(UUID id) {
        return teamMemberJpaRepository.findById(id)
                .map(TeamMemberMapper::toDomain);
    }

    @Override
    public Optional<TeamMember> findByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId) {
        return teamMemberJpaRepository.findByTeamIdAndWorkspaceMemberId(teamId, workspaceMemberId)
                .map(TeamMemberMapper::toDomain);
    }

    @Override
    public List<TeamMember> findByTeamId(UUID teamId) {
        return teamMemberJpaRepository.findByTeamId(teamId)
                .stream()
                .map(TeamMemberMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamMember> findByWorkspaceMemberId(UUID workspaceMemberId) {
        return teamMemberJpaRepository.findByWorkspaceMemberId(workspaceMemberId)
                .stream()
                .map(TeamMemberMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public TeamMember save(TeamMember teamMember) {
        TeamMemberEntity entity = TeamMemberMapper.toEntity(teamMember);
        TeamMemberEntity savedEntity = teamMemberJpaRepository.save(entity);
        return TeamMemberMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId) {
        return teamMemberJpaRepository.existsByTeamIdAndWorkspaceMemberId(teamId, workspaceMemberId);
    }

    @Override
    public void deleteById(UUID id) {
        teamMemberJpaRepository.deleteById(id);
    }
}
