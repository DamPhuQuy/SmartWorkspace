package com.workspace.infrastructure.adapter.in.web.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import com.workspace.application.port.in.role.AssignWorkspaceMemberRoleUseCase;
import com.workspace.application.port.in.role.CreateRoleUseCase;
import com.workspace.application.port.in.role.DeleteRoleUseCase;
import com.workspace.domain.model.role.Role;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;
import com.workspace.infrastructure.adapter.in.web.common.ApiResponse;
import com.workspace.infrastructure.adapter.in.web.dto.RoleDto;
import com.workspace.infrastructure.adapter.in.web.mapper.RoleWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Role management and assignment within workspaces")
public class RoleController {

    private final CreateRoleUseCase createRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final AssignWorkspaceMemberRoleUseCase assignWorkspaceMemberRoleUseCase;

    @PostMapping
    @Operation(summary = "Create role", description = "Creates a new role in a specific workspace")
    public ApiResponse<RoleDto.RoleResponse> createRole(@RequestBody RoleDto.CreateRoleRequest request) {
        CreateRoleUseCase.Command command = new CreateRoleUseCase.Command(
            request.workspaceId(),
            request.name()
        );
        Role role = createRoleUseCase.createRole(command);
        return ApiResponse.success("Role created successfully", RoleWebMapper.toResponse(role));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role", description = "Deletes a role by its unique ID")
    public ApiResponse<Void> deleteRole(@PathVariable UUID id) {
        deleteRoleUseCase.deleteRole(id);
        return ApiResponse.success("Role deleted successfully", null);
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign role", description = "Assigns a workspace role to a workspace member")
    public ApiResponse<RoleDto.WorkspaceMemberRoleResponse> assignRole(@RequestBody RoleDto.AssignRoleRequest request) {
        AssignWorkspaceMemberRoleUseCase.Command command = new AssignWorkspaceMemberRoleUseCase.Command(
            request.workspaceMemberId(),
            request.roleId()
        );
        WorkspaceMemberRole assignedRole = assignWorkspaceMemberRoleUseCase.assignRole(command);
        return ApiResponse.success("Role assigned successfully", RoleWebMapper.toMemberRoleResponse(assignedRole));
    }
}
