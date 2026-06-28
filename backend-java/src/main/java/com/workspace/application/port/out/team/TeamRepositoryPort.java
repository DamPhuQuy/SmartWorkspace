package com.workspace.application.port.out.team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.team.Team;
import com.workspace.domain.model.team.TeamMember;

public interface TeamRepositoryPort {
    Optional<Team> findById(UUID id);
    Optional<Team> findByWorkspaceIdAndName(UUID workspaceId, String name);
    List<Team> findByWorkspaceId(UUID workspaceId);
    Team save(Team team);
    boolean existsByWorkspaceIdAndName(UUID workspaceId, String name);
    void deleteById(UUID id);

    // TeamMember methods
    Optional<TeamMember> findMemberById(UUID id);
    Optional<TeamMember> findMemberByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId);
    List<TeamMember> findMembersByTeamId(UUID teamId);
    List<TeamMember> findMembersByWorkspaceMemberId(UUID workspaceMemberId);
    TeamMember saveMember(TeamMember teamMember);
    boolean existsMemberByTeamIdAndWorkspaceMemberId(UUID teamId, UUID workspaceMemberId);
    void deleteMemberById(UUID id);
}
