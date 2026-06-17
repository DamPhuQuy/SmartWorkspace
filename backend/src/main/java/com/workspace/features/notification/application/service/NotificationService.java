package com.workspace.features.notification.application.service;

import com.workspace.features.notification.application.dto.*;
import com.workspace.features.notification.application.service.*;
import com.workspace.features.notification.infrastructure.persistence.entity.*;
import com.workspace.features.notification.infrastructure.persistence.jpaRepo.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationDto> getNotificationsForUser(User user) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(NotificationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public NotificationDto markAsRead(UUID id, User currentUser) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        if (!notification.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("Access denied");
        }

        notification.setRead(true);
        return NotificationDto.fromEntity(notificationRepository.save(notification));
    }

    @Transactional
    public void markAllAsRead(User currentUser) {
        List<Notification> unread = notificationRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId()).stream()
                .filter(n -> !n.isRead())
                .collect(Collectors.toList());

        for (Notification notification : unread) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(unread);
    }

    @Transactional
    public void sendNotification(User user, String title, String content, String type, String link) {
        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .content(content)
                .type(type)
                .link(link)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }
}
