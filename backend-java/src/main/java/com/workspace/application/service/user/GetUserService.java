package com.workspace.application.service.user;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.user.GetUserUseCase;
import com.workspace.application.port.out.user.UserRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }
}
