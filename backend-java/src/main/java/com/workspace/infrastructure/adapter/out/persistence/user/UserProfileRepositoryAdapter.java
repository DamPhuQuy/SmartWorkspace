package com.workspace.infrastructure.adapter.out.persistence.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.user.UserProfileRepositoryPort;
import com.workspace.domain.model.user.UserProfile;
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
