package com.workspace.infrastructure.adapter.in.web.mapper;

import com.workspace.domain.model.warning.Warning;
import com.workspace.infrastructure.adapter.in.web.dto.WarningDto;

public class WarningWebMapper {

    private WarningWebMapper() {}

    public static WarningDto.WarningResponse toResponse(Warning warning) {
        if (warning == null) {
            return null;
        }

        return new WarningDto.WarningResponse(
            warning.getId(),
            warning.getWorkspaceMemberId(),
            warning.getWarningType() != null ? warning.getWarningType().name() : null,
            warning.getDescription(),
            warning.getCreatedAt(),
            warning.getUpdatedAt()
        );
    }
}
