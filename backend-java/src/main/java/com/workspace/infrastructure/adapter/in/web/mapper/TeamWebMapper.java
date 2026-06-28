package com.workspace.infrastructure.adapter.in.web.mapper;

import java.util.UUID;
import com.workspace.domain.model.team.Team;
import com.workspace.domain.model.team.TeamMember;
import com.workspace.infrastructure.adapter.in.web.dto.TeamDto;

public class TeamWebMapper {

    private TeamWebMapper() {}

    public static TeamDto.TeamResponse toResponse(Team team) {
        if (team == null) {
            return null;
        }

        UUID workspaceId = team.getWorkspace() != null ? team.getWorkspace().getId() : null;

        return new TeamDto.TeamResponse(
            team.getId(),
            workspaceId,
            team.getName(),
            team.getCreatedAt(),
            team.getUpdatedAt()
        );
    }

    public static TeamDto.TeamMemberResponse toMemberResponse(TeamMember member) {
        if (member == null) {
            return null;
        }

        UUID teamId = member.getTeam() != null ? member.getTeam().getId() : null;

        return new TeamDto.TeamMemberResponse(
            member.getId(),
            teamId,
            WorkspaceWebMapper.toMemberResponse(member.getWorkspaceMember()),
            member.getJoinedAt()
        );
    }
}
