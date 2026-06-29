package com.workspace.infrastructure.adapter.out.persistence.letter.repository;

import com.workspace.domain.model.letter.Letter;
import com.workspace.infrastructure.adapter.out.persistence.letter.entity.LetterEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.repository.WorkspaceMemberMapper;


public class LetterMapper {

    private LetterMapper() {}

    public static Letter toDomain(LetterEntity entity) {
        if (entity == null) {
            return null;
        }

        return Letter.builder()
                .id(entity.getId())
                .workspaceMember(WorkspaceMemberMapper.toDomain(entity.getWorkspaceMember()))
                .letterType(entity.getLetterType())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static LetterEntity toEntity(Letter domain) {
        if (domain == null) {
            return null;
        }

        return LetterEntity.builder()
                .id(domain.getId())
                .workspaceMember(WorkspaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .letterType(domain.getLetterType())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
