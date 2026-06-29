package com.workspace.infrastructure.adapter.out.persistence.team.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.team.entity.TeamMemberEntity;

public interface TeamMemberJpaRepository extends JpaRepository<TeamMemberEntity, UUID> {
    Optional<TeamMemberEntity> findByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId);
    List<TeamMemberEntity> findByTeamId(UUID teamId);
    List<TeamMemberEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
    boolean existsByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId);
}
