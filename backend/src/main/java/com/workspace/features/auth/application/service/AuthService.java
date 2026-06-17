package com.workspace.features.auth.application.service;

import com.workspace.features.auth.application.dto.*;
import com.workspace.features.auth.application.service.*;
import com.workspace.features.auth.infrastructure.config.*;
import com.workspace.features.auth.infrastructure.persistence.entity.*;
import com.workspace.features.auth.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.auth.infrastructure.web.dto.*;

import com.workspace.features.auth.application.dto.AuthResponse;
import com.workspace.features.auth.infrastructure.persistence.entity.RefreshToken;
import com.workspace.features.auth.infrastructure.web.dto.LoginRequest;
import com.workspace.features.auth.infrastructure.web.dto.LogoutRequest;
import com.workspace.features.auth.infrastructure.web.dto.RefreshTokenRequest;
import com.workspace.features.auth.infrastructure.web.dto.RegisterRequest;
import com.workspace.features.user.infrastructure.persistence.entity.User;
import com.workspace.features.user.application.dto.UserDto;
import com.workspace.features.user.infrastructure.persistence.jpaRepo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .build();
        userRepository.save(user);
        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return buildAuthResponse(user);
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        String accessToken = jwtService.generateToken(refreshToken.getUser().getUsername());
        return AuthResponse.builder()
                .token(accessToken)
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .user(UserDto.fromEntity(refreshToken.getUser()))
                .build();
    }

    public void logout(LogoutRequest request) {
        refreshTokenService.revoke(request.getRefreshToken());
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtService.generateToken(user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return AuthResponse.builder()
                .token(accessToken)
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .user(UserDto.fromEntity(user))
                .build();
    }
}
