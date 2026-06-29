package com.workspace.infrastructure.adapter.in.web.controller;

import org.springframework.web.bind.annotation.*;

import com.workspace.application.port.in.warning.command.IssueWarningUseCase;
import com.workspace.domain.model.warning.Warning;
import com.workspace.infrastructure.adapter.in.web.common.ApiResponse;
import com.workspace.infrastructure.adapter.in.web.dto.WarningDto;
import com.workspace.infrastructure.adapter.in.web.mapper.WarningWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/warnings")
@RequiredArgsConstructor
@Tag(name = "Warnings", description = "Issuing and managing workspace violations and warnings")
public class WarningController {

    private final IssueWarningUseCase issueWarningUseCase;

    @PostMapping
    @Operation(summary = "Issue a warning", description = "Issues a warning to a specific workspace member due to policy violations")
    public ApiResponse<WarningDto.WarningResponse> issueWarning(@RequestBody WarningDto.IssueWarningRequest request) {
        IssueWarningUseCase.Command command = new IssueWarningUseCase.Command(
            request.workspaceMemberId(),
            request.warningType(),
            request.description()
        );
        Warning warning = issueWarningUseCase.issueWarning(command);
        return ApiResponse.success("Warning issued successfully", WarningWebMapper.toResponse(warning));
    }
}
