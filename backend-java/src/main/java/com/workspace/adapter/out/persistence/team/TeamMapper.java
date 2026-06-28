package com.workspace.adapter.out.persistence.team;

import com.workspace.domain.model.team.Team;
import com.workspace.adapter.out.persistence.workspace.WorkSpaceMapper;

public class TeamMapper {

    private TeamMapper() {}

    public static Team toDomain(TeamEntity entity) {
        if (entity == null) {
            return null;
        }

        return Team.builder()
                .id(entity.getId())
                .workspace(WorkSpaceMapper.toDomain(entity.getWorkspace()))
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static TeamEntity toEntity(Team domain) {
        if (domain == null) {
            return null;
        }

        return TeamEntity.builder()
                .id(domain.getId())
                .workspace(WorkSpaceMapper.toEntity(domain.getWorkspace()))
                .name(domain.getName())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
