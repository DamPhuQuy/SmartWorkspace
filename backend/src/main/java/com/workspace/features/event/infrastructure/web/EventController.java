package com.workspace.features.event.infrastructure.web;

import com.workspace.features.event.application.dto.*;
import com.workspace.features.event.application.service.*;
import com.workspace.features.event.infrastructure.persistence.entity.*;
import com.workspace.features.event.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.event.infrastructure.web.dto.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/api/workspaces/{workspaceId}/events")
    @PreAuthorize("hasAuthority('*') or hasAuthority('event:create')")
    public ResponseEntity<EventDto> createEvent(
            @PathVariable UUID workspaceId,
            @RequestBody CreateEventRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(eventService.createEvent(workspaceId, request, user));
    }

    @GetMapping("/api/workspaces/{workspaceId}/events")
    @PreAuthorize("hasAuthority('*') or hasAuthority('event:read')")
    public ResponseEntity<List<EventDto>> getEvents(
            @PathVariable UUID workspaceId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(eventService.getEventsForWorkspace(workspaceId, user));
    }

    @GetMapping("/api/events/{eventId}/attendees")
    @PreAuthorize("hasAuthority('*') or hasAuthority('event:read')")
    public ResponseEntity<List<EventAttendeeDto>> getEventAttendees(
            @PathVariable UUID eventId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(eventService.getEventAttendees(eventId, user));
    }

    @PutMapping("/api/events/{eventId}/rsvp")
    @PreAuthorize("hasAuthority('*') or hasAuthority('event:respond')")
    public ResponseEntity<EventAttendeeDto> updateRsvp(
            @PathVariable UUID eventId,
            @RequestParam String status,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(eventService.updateRsvp(eventId, status, user));
    }

    @DeleteMapping("/api/events/{eventId}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('event:delete')")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable UUID eventId,
            @AuthenticationPrincipal User user
    ) {
        eventService.deleteEvent(eventId, user);
        return ResponseEntity.noContent().build();
    }
}
