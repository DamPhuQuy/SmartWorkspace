package com.workspace.infrastructure.adapter.out.persistence.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.user.UserRepositoryPort;
import com.workspace.domain.model.user.User;
import com.workspace.domain.model.user.UserProfile;
import com.workspace.infrastructure.adapter.out.persistence.user.entity.UserEntity;
import com.workspace.infrastructure.adapter.out.persistence.user.entity.UserProfileEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserProfileJpaRepository userProfileJpaRepository;

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return userJpaRepository.findByPhone(phone)
                .map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return UserMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userJpaRepository.existsByPhone(phone);
    }

    @Override
    public void deleteById(UUID id) {
        userJpaRepository.deleteById(id);
    }

    // UserProfile methods
    @Override
    public Optional<UserProfile> findProfileByUserId(UUID userId) {
        return userProfileJpaRepository.findById(userId)
                .map(UserProfileMapper::toDomain);
    }

    @Override
    public UserProfile saveProfile(UserProfile userProfile) {
        UserProfileEntity entity = UserProfileMapper.toEntity(userProfile);
        UserProfileEntity savedEntity = userProfileJpaRepository.save(entity);
        return UserProfileMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteProfileByUserId(UUID userId) {
        userProfileJpaRepository.deleteById(userId);
    }
}
