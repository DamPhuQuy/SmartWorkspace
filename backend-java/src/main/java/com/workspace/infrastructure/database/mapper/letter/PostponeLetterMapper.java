package com.workspace.infrastructure.database.mapper.letter;

import com.workspace.domain.model.letter.PostponeLetter;
import com.workspace.infrastructure.database.entity.letter.PostponeLetterEntity;
import com.workspace.infrastructure.database.mapper.assignment.AssignmentMapper;

public class PostponeLetterMapper {

    private PostponeLetterMapper() {}

    public static PostponeLetter toDomain(PostponeLetterEntity entity) {
        if (entity == null) {
            return null;
        }

        return PostponeLetter.builder()
                .letterId(entity.getLetterId())
                .letter(LetterMapper.toDomain(entity.getLetter()))
                .assignment(AssignmentMapper.toDomain(entity.getAssignment()))
                .assignmentSnapshot(entity.getAssignmentSnapshot())
                .oldDeadline(entity.getOldDeadline())
                .requestedDeadline(entity.getRequestedDeadline())
                .build();
    }

    public static PostponeLetterEntity toEntity(PostponeLetter domain) {
        if (domain == null) {
            return null;
        }

        return PostponeLetterEntity.builder()
                .letterId(domain.getLetterId())
                .letter(LetterMapper.toEntity(domain.getLetter()))
                .assignment(AssignmentMapper.toEntity(domain.getAssignment()))
                .assignmentSnapshot(domain.getAssignmentSnapshot())
                .oldDeadline(domain.getOldDeadline())
                .requestedDeadline(domain.getRequestedDeadline())
                .build();
    }
}
