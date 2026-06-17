package com.workspace.features.event.infrastructure.persistence.jpaRepo;

import com.workspace.features.event.application.dto.*;
import com.workspace.features.event.application.service.*;
import com.workspace.features.event.infrastructure.persistence.entity.*;
import com.workspace.features.event.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.event.infrastructure.web.dto.*;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventAttendeeRepository extends JpaRepository<EventAttendee, EventAttendeeId> {
    List<EventAttendee> findByEventId(UUID eventId);
    Optional<EventAttendee> findByEventIdAndUserId(UUID eventId, UUID userId);
}
