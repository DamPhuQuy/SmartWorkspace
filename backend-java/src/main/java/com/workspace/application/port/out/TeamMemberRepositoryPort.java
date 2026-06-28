package com.workspace.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.team.TeamMember;

public interface TeamMemberRepositoryPort {
    Optional<TeamMember> findById(UUID id);
    Optional<TeamMember> findByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId);
    List<TeamMember> findByTeamId(UUID teamId);
    List<TeamMember> findByWorkspaceMemberId(UUID workspaceMemberId);
    TeamMember save(TeamMember teamMember);
    boolean existsByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId);
    void deleteById(UUID id);
}
