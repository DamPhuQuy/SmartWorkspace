package com.workspace.infrastructure.adapter.out.persistence.team;

import com.workspace.domain.model.team.TeamMember;
import com.workspace.infrastructure.adapter.out.persistence.workspace.WorkspaceMemberMapper;

public class TeamMemberMapper {

    private TeamMemberMapper() {}

    public static TeamMember toDomain(TeamMemberEntity entity) {
        if (entity == null) {
            return null;
        }

        return TeamMember.builder()
                .id(entity.getId())
                .team(TeamMapper.toDomain(entity.getTeam()))
                .workspaceMember(WorkspaceMemberMapper.toDomain(entity.getWorkspaceMember()))
                .joinedAt(entity.getJoinedAt())
                .build();
    }

    public static TeamMemberEntity toEntity(TeamMember domain) {
        if (domain == null) {
            return null;
        }

        return TeamMemberEntity.builder()
                .id(domain.getId())
                .team(TeamMapper.toEntity(domain.getTeam()))
                .workspaceMember(WorkspaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .joinedAt(domain.getJoinedAt())
                .build();
    }
}
