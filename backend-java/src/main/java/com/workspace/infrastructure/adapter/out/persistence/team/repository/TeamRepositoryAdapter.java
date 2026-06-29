package com.workspace.infrastructure.adapter.out.persistence.team.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.team.TeamRepositoryPort;
import com.workspace.domain.model.team.Team;
import com.workspace.domain.model.team.TeamMember;
import com.workspace.infrastructure.adapter.out.persistence.team.entity.TeamEntity;
import com.workspace.infrastructure.adapter.out.persistence.team.entity.TeamMemberEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryAdapter implements TeamRepositoryPort {

    private final TeamJpaRepository teamJpaRepository;
    private final TeamMemberJpaRepository teamMemberJpaRepository;

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
                .toList();
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

    // TeamMember methods
    @Override
    public Optional<TeamMember> findMemberById(UUID id) {
        return teamMemberJpaRepository.findById(id)
                .map(TeamMemberMapper::toDomain);
    }

    @Override
    public Optional<TeamMember> findMemberByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId) {
        return teamMemberJpaRepository.findByTeamIdAndWorkspaceMemberId(teamId, workspaceMemberId)
                .map(TeamMemberMapper::toDomain);
    }

    @Override
    public List<TeamMember> findMembersByTeamId(UUID teamId) {
        return teamMemberJpaRepository.findByTeamId(teamId)
                .stream()
                .map(TeamMemberMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeamMember> findMembersByWorkspaceMemberId(UUID workspaceMemberId) {
        return teamMemberJpaRepository.findByWorkspaceMemberId(workspaceMemberId)
                .stream()
                .map(TeamMemberMapper::toDomain)
                .toList();
    }

    @Override
    public TeamMember saveMember(TeamMember teamMember) {
        TeamMemberEntity entity = TeamMemberMapper.toEntity(teamMember);
        TeamMemberEntity savedEntity = teamMemberJpaRepository.save(entity);
        return TeamMemberMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsMemberByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId) {
        return teamMemberJpaRepository.existsByTeamIdAndWorkspaceMemberId(teamId, workspaceMemberId);
    }

    @Override
    public void deleteMemberById(UUID id) {
        teamMemberJpaRepository.deleteById(id);
    }
}
