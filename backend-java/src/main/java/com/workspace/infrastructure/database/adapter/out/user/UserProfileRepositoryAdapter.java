package com.workspace.infrastructure.database.adapter.out.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.user.UserProfileRepositoryPort;
import com.workspace.domain.model.user.UserProfile;
import com.workspace.infrastructure.database.entity.user.UserProfileEntity;
import com.workspace.infrastructure.database.mapper.user.UserProfileMapper;
import com.workspace.infrastructure.database.repository.user.UserProfileJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryAdapter implements UserProfileRepositoryPort {

    private final UserProfileJpaRepository userProfileJpaRepository;

    @Override
    public Optional<UserProfile> findByUserId(UUID userId) {
        return userProfileJpaRepository.findById(userId)
                .map(UserProfileMapper::toDomain);
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        UserProfileEntity entity = UserProfileMapper.toEntity(userProfile);
        UserProfileEntity savedEntity = userProfileJpaRepository.save(entity);
        return UserProfileMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteByUserId(UUID userId) {
        userProfileJpaRepository.deleteById(userId);
    }
}
