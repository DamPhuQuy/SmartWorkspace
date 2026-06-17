package com.workspace.features.auth.application.service;

import com.workspace.features.auth.application.dto.*;
import com.workspace.features.auth.application.service.*;
import com.workspace.features.auth.infrastructure.config.*;
import com.workspace.features.auth.infrastructure.persistence.entity.*;
import com.workspace.features.auth.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.auth.infrastructure.web.dto.*;

import com.workspace.features.user.infrastructure.persistence.jpaRepo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkspaceUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserAuthorityService userAuthorityService;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .map(userAuthorityService::attachAuthorities)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
