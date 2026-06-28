package com.workspace.adapter.out.persistence.team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberJpaRepository extends JpaRepository<TeamMemberEntity, UUID> {
    Optional<TeamMemberEntity> findByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId);
    List<TeamMemberEntity> findByTeamId(UUID teamId);
    List<TeamMemberEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
    boolean existsByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId);
}
