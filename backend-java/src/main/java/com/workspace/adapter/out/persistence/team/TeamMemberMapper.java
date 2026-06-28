package com.workspace.adapter.out.persistence.team;

import com.workspace.domain.model.team.TeamMember;
import com.workspace.adapter.out.persistence.workspace.WorkSpaceMemberMapper;

public class TeamMemberMapper {

    private TeamMemberMapper() {}

    public static TeamMember toDomain(TeamMemberEntity entity) {
        if (entity == null) {
            return null;
        }

        return TeamMember.builder()
                .id(entity.getId())
                .team(TeamMapper.toDomain(entity.getTeam()))
                .workspaceMember(WorkSpaceMemberMapper.toDomain(entity.getWorkspaceMember()))
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
                .workspaceMember(WorkSpaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .joinedAt(domain.getJoinedAt())
                .build();
    }
}
