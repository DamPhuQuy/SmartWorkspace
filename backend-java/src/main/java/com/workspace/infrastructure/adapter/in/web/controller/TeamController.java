package com.workspace.infrastructure.adapter.in.web.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import com.workspace.application.port.in.team.command.AddTeamMemberUseCase;
import com.workspace.application.port.in.team.command.CreateTeamUseCase;
import com.workspace.application.port.in.team.command.DeleteTeamUseCase;
import com.workspace.application.port.in.team.command.RemoveTeamMemberUseCase;
import com.workspace.domain.model.team.Team;
import com.workspace.domain.model.team.TeamMember;
import com.workspace.infrastructure.adapter.in.web.common.ApiResponse;
import com.workspace.infrastructure.adapter.in.web.dto.TeamDto;
import com.workspace.infrastructure.adapter.in.web.mapper.TeamWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@Tag(name = "Teams", description = "Team management and team membership within workspaces")
public class TeamController {

    private final CreateTeamUseCase createTeamUseCase;
    private final DeleteTeamUseCase deleteTeamUseCase;
    private final AddTeamMemberUseCase addTeamMemberUseCase;
    private final RemoveTeamMemberUseCase removeTeamMemberUseCase;

    @PostMapping
    @Operation(summary = "Create team", description = "Creates a new team inside a specific workspace")
    public ApiResponse<TeamDto.TeamResponse> createTeam(@RequestBody TeamDto.CreateTeamRequest request) {
        CreateTeamUseCase.Command command = new CreateTeamUseCase.Command(
            request.workspaceId(),
            request.name()
        );
        Team team = createTeamUseCase.createTeam(command);
        return ApiResponse.success("Team created successfully", TeamWebMapper.toResponse(team));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete team", description = "Deletes a team by its unique ID")
    public ApiResponse<Void> deleteTeam(@PathVariable UUID id) {
        deleteTeamUseCase.deleteTeam(id);
        return ApiResponse.success("Team deleted successfully", null);
    }

    @PostMapping("/{id}/members")
    @Operation(summary = "Add team member", description = "Adds a workspace member to a specific team")
    public ApiResponse<TeamDto.TeamMemberResponse> addMember(
            @PathVariable UUID id,
            @RequestBody TeamDto.AddTeamMemberRequest request) {
        AddTeamMemberUseCase.Command command = new AddTeamMemberUseCase.Command(
            id,
            request.workspaceMemberId()
        );
        TeamMember teamMember = addTeamMemberUseCase.addTeamMember(command);
        return ApiResponse.success("Team member added successfully", TeamWebMapper.toMemberResponse(teamMember));
    }

    @DeleteMapping("/{id}/members/{workspaceMemberId}")
    @Operation(summary = "Remove team member", description = "Removes a member from a team")
    public ApiResponse<Void> removeMember(
            @PathVariable UUID id,
            @PathVariable UUID workspaceMemberId) {
        RemoveTeamMemberUseCase.Command command = new RemoveTeamMemberUseCase.Command(
            id,
            workspaceMemberId
        );
        removeTeamMemberUseCase.removeTeamMember(command);
        return ApiResponse.success("Team member removed successfully", null);
    }
}
