package com.workspace.infrastructure.adapter.in.web.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import com.workspace.application.port.in.workspace.command.AddWorkspaceMemberUseCase;
import com.workspace.application.port.in.workspace.command.CreateWorkspaceUseCase;
import com.workspace.application.port.in.workspace.command.DeleteWorkspaceUseCase;
import com.workspace.application.port.in.workspace.command.RemoveWorkspaceMemberUseCase;
import com.workspace.application.port.in.workspace.command.UpdateWorkspaceUseCase;
import com.workspace.domain.model.workspace.Workspace;
import com.workspace.domain.model.workspace.WorkspaceMember;
import com.workspace.infrastructure.adapter.in.web.common.ApiResponse;
import com.workspace.infrastructure.adapter.in.web.dto.WorkspaceDto;
import com.workspace.infrastructure.adapter.in.web.mapper.WorkspaceWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
@Tag(name = "Workspaces", description = "Workspace management and membership")
public class WorkspaceController {

    private final CreateWorkspaceUseCase createWorkspaceUseCase;
    private final UpdateWorkspaceUseCase updateWorkspaceUseCase;
    private final DeleteWorkspaceUseCase deleteWorkspaceUseCase;
    private final AddWorkspaceMemberUseCase addWorkspaceMemberUseCase;
    private final RemoveWorkspaceMemberUseCase removeWorkspaceMemberUseCase;

    @PostMapping
    @Operation(summary = "Create workspace", description = "Creates a new workspace with a name, slug, description and an owner")
    public ApiResponse<WorkspaceDto.WorkspaceResponse> createWorkspace(@RequestBody WorkspaceDto.CreateWorkspaceRequest request) {
        CreateWorkspaceUseCase.Command command = new CreateWorkspaceUseCase.Command(
            request.name(),
            request.slug(),
            request.description(),
            request.ownerId()
        );
        Workspace workspace = createWorkspaceUseCase.createWorkspace(command);
        return ApiResponse.success("Workspace created successfully", WorkspaceWebMapper.toResponse(workspace));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update workspace", description = "Updates details of a specific workspace")
    public ApiResponse<WorkspaceDto.WorkspaceResponse> updateWorkspace(
            @PathVariable UUID id,
            @RequestBody WorkspaceDto.UpdateWorkspaceRequest request) {
        UpdateWorkspaceUseCase.Command command = new UpdateWorkspaceUseCase.Command(
            id,
            request.name(),
            request.slug(),
            request.description()
        );
        Workspace workspace = updateWorkspaceUseCase.updateWorkspace(command);
        return ApiResponse.success("Workspace updated successfully", WorkspaceWebMapper.toResponse(workspace));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete workspace", description = "Permanently deletes a workspace by its unique ID")
    public ApiResponse<Void> deleteWorkspace(@PathVariable UUID id) {
        deleteWorkspaceUseCase.deleteWorkspace(id);
        return ApiResponse.success("Workspace deleted successfully", null);
    }

    @PostMapping("/{id}/members")
    @Operation(summary = "Add workspace member", description = "Adds a user to a workspace as a member")
    public ApiResponse<WorkspaceDto.WorkspaceMemberResponse> addMember(
            @PathVariable UUID id,
            @RequestBody WorkspaceDto.AddMemberRequest request) {
        AddWorkspaceMemberUseCase.Command command = new AddWorkspaceMemberUseCase.Command(
            id,
            request.userId()
        );
        WorkspaceMember member = addWorkspaceMemberUseCase.addMember(command);
        return ApiResponse.success("Workspace member added successfully", WorkspaceWebMapper.toMemberResponse(member));
    }

    @DeleteMapping("/{id}/members/{userId}")
    @Operation(summary = "Remove workspace member", description = "Removes a user from a workspace's membership list")
    public ApiResponse<Void> removeMember(
            @PathVariable UUID id,
            @PathVariable UUID userId) {
        RemoveWorkspaceMemberUseCase.Command command = new RemoveWorkspaceMemberUseCase.Command(
            id,
            userId
        );
        removeWorkspaceMemberUseCase.removeMember(command);
        return ApiResponse.success("Workspace member removed successfully", null);
    }
}
