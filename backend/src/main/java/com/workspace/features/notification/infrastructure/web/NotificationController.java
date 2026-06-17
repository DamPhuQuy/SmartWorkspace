package com.workspace.features.notification.infrastructure.web;

import com.workspace.features.notification.application.dto.*;
import com.workspace.features.notification.application.service.*;
import com.workspace.features.notification.infrastructure.persistence.entity.*;
import com.workspace.features.notification.infrastructure.persistence.jpaRepo.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasAuthority('*') or hasAuthority('notification:read')")
    public ResponseEntity<List<NotificationDto>> getNotifications(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(user));
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("hasAuthority('*') or hasAuthority('notification:update')")
    public ResponseEntity<NotificationDto> markAsRead(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(notificationService.markAsRead(id, user));
    }

    @PutMapping("/read-all")
    @PreAuthorize("hasAuthority('*') or hasAuthority('notification:update')")
    public ResponseEntity<Void> markAllAsRead(@AuthenticationPrincipal User user) {
        notificationService.markAllAsRead(user);
        return ResponseEntity.ok().build();
    }
}
