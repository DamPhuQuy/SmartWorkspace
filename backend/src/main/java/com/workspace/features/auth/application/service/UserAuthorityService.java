package com.workspace.features.auth.application.service;

import com.workspace.features.auth.application.dto.*;
import com.workspace.features.auth.application.service.*;
import com.workspace.features.auth.infrastructure.config.*;
import com.workspace.features.auth.infrastructure.persistence.entity.*;
import com.workspace.features.auth.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.auth.infrastructure.web.dto.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import com.workspace.features.workspace.infrastructure.persistence.entity.Permission;
import com.workspace.features.workspace.infrastructure.persistence.entity.Role;
import com.workspace.features.workspace.infrastructure.persistence.entity.WorkspaceMember;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthorityService {

    private final WorkspaceMemberRepository workspaceMemberRepository;

    public User attachAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = workspaceMemberRepository.findByUserIdWithRolePermissions(user.getId()).stream()
                .map(WorkspaceMember::getRole)
                .filter(role -> role != null)
                .flatMap(role -> permissionNames(role).stream())
                .filter(permission -> !permission.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        user.setAuthorities(Set.copyOf(authorities));
        return user;
    }

    private Set<String> permissionNames(Role role) {
        Set<String> names = role.getPermissionSet().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        if (role.getPermissions() != null && !role.getPermissions().isBlank()) {
            names.addAll(Arrays.stream(role.getPermissions().split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet()));
        }
        return names;
    }
}
