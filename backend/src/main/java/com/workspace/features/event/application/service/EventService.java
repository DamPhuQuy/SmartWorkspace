package com.workspace.features.event.application.service;

import com.workspace.features.event.application.dto.*;
import com.workspace.features.event.application.service.*;
import com.workspace.features.event.infrastructure.persistence.entity.*;
import com.workspace.features.event.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.event.infrastructure.web.dto.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import com.workspace.features.user.infrastructure.persistence.jpaRepo.UserRepository;
import com.workspace.features.workspace.infrastructure.persistence.entity.Workspace;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.WorkspaceRepository;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventAttendeeRepository eventAttendeeRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public EventDto createEvent(UUID workspaceId, CreateEventRequest request, User organizer) {
        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspaceId, organizer.getId())) {
            throw new IllegalStateException("You are not a member of this workspace");
        }

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("Workspace not found"));

        Event event = Event.builder()
                .workspace(workspace)
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .organizer(organizer)
                .build();
        event = eventRepository.save(event);

        EventAttendee organizerAttendee = EventAttendee.builder()
                .event(event)
                .user(organizer)
                .status("ACCEPTED")
                .build();
        eventAttendeeRepository.save(organizerAttendee);

        if (request.getAttendeeIds() != null) {
            for (UUID userId : request.getAttendeeIds()) {
                if (userId.equals(organizer.getId())) continue;
                User user = userRepository.findById(userId)
                        .orElse(null);
                if (user != null && workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspaceId, userId)) {
                    EventAttendee attendee = EventAttendee.builder()
                            .event(event)
                            .user(user)
                            .status("PENDING")
                            .build();
                    eventAttendeeRepository.save(attendee);
                }
            }
        }

        return EventDto.fromEntity(event);
    }

    public List<EventDto> getEventsForWorkspace(UUID workspaceId, User currentUser) {
        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(workspaceId, currentUser.getId())) {
            throw new IllegalStateException("You are not a member of this workspace");
        }

        return eventRepository.findByWorkspaceId(workspaceId).stream()
                .map(EventDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<EventAttendeeDto> getEventAttendees(UUID eventId, User currentUser) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (!workspaceMemberRepository.existsByWorkspaceIdAndUserId(event.getWorkspace().getId(), currentUser.getId())) {
            throw new IllegalStateException("You do not have access to this event");
        }

        return eventAttendeeRepository.findByEventId(eventId).stream()
                .map(EventAttendeeDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public EventAttendeeDto updateRsvp(UUID eventId, String status, User currentUser) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        EventAttendee attendee = eventAttendeeRepository.findByEventIdAndUserId(eventId, currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("You are not invited to this event"));

        if (!status.equals("ACCEPTED") && !status.equals("DECLINED") && !status.equals("PENDING")) {
            throw new IllegalArgumentException("Invalid RSVP status");
        }

        attendee.setStatus(status);
        return EventAttendeeDto.fromEntity(eventAttendeeRepository.save(attendee));
    }

    @Transactional
    public void deleteEvent(UUID eventId, User currentUser) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        boolean isOrganizer = event.getOrganizer() != null && event.getOrganizer().getId().equals(currentUser.getId());
        
        if (!isOrganizer) {
            var member = workspaceMemberRepository.findByWorkspaceIdAndUserId(event.getWorkspace().getId(), currentUser.getId())
                    .orElseThrow(() -> new IllegalStateException("You do not have access to this event"));
            
            if (member.getRole() == null || 
                (!member.getRole().getName().equals("OWNER") && !member.getRole().getName().equals("ADMIN"))) {
                throw new IllegalStateException("You do not have permission to delete this event");
            }
        }

        eventRepository.delete(event);
    }
}
