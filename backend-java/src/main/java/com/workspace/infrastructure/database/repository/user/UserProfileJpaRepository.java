package com.workspace.infrastructure.database.repository.user;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.user.UserProfileEntity;

public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity, UUID> {
}
