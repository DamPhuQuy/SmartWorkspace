package com.workspace.infrastructure.adapter.in.web.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import com.workspace.application.port.in.user.CreateUserUseCase;
import com.workspace.application.port.in.user.GetUserUseCase;
import com.workspace.application.port.in.user.UpdateUserProfileUseCase;
import com.workspace.domain.model.user.User;
import com.workspace.domain.model.user.UserProfile;
import com.workspace.infrastructure.adapter.in.web.common.ApiResponse;
import com.workspace.infrastructure.adapter.in.web.dto.UserDto;
import com.workspace.infrastructure.adapter.in.web.mapper.UserWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User registration, retrieval, and profile management")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;

    @PostMapping
    @Operation(summary = "Register a new user", description = "Creates a new user and their corresponding profile")
    public ApiResponse<UserDto.UserResponse> createUser(@RequestBody UserDto.CreateUserRequest request) {
        CreateUserUseCase.Command command = new CreateUserUseCase.Command(
            request.email(),
            request.password(),
            request.phone(),
            request.firstName(),
            request.lastName(),
            request.avatarUrl(),
            request.bio(),
            request.discordId()
        );
        User user = createUserUseCase.createUser(command);
        return ApiResponse.success("User created successfully", UserWebMapper.toResponse(user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user's details including their profile using their unique ID")
    public ApiResponse<UserDto.UserResponse> getUser(@PathVariable UUID id) {
        User user = getUserUseCase.getUserById(id);
        return ApiResponse.success(UserWebMapper.toResponse(user));
    }

    @PutMapping("/{id}/profile")
    @Operation(summary = "Update user profile", description = "Updates the profile details (names, bio, avatar, discord) of a specific user")
    public ApiResponse<UserDto.UserProfileResponse> updateProfile(
            @PathVariable UUID id,
            @RequestBody UserDto.UpdateUserProfileRequest request) {
        UpdateUserProfileUseCase.Command command = new UpdateUserProfileUseCase.Command(
            id,
            request.firstName(),
            request.lastName(),
            request.avatarUrl(),
            request.bio(),
            request.discordId()
        );
        UserProfile profile = updateUserProfileUseCase.updateUserProfile(command);
        return ApiResponse.success("Profile updated successfully", UserWebMapper.toProfileResponse(profile));
    }
}
