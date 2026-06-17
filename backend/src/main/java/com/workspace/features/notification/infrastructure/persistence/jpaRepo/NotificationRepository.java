package com.workspace.features.notification.infrastructure.persistence.jpaRepo;

import com.workspace.features.notification.application.dto.*;
import com.workspace.features.notification.application.service.*;
import com.workspace.features.notification.infrastructure.persistence.entity.*;
import com.workspace.features.notification.infrastructure.persistence.jpaRepo.*;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
