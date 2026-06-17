package com.workspace.features.event.infrastructure.persistence.entity;

import com.workspace.features.event.application.dto.*;
import com.workspace.features.event.application.service.*;
import com.workspace.features.event.infrastructure.persistence.entity.*;
import com.workspace.features.event.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.event.infrastructure.web.dto.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class EventAttendeeId implements Serializable {
    private UUID event;
    private UUID user;

    public EventAttendeeId() {}

    public EventAttendeeId(UUID event, UUID user) {
        this.event = event;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventAttendeeId that = (EventAttendeeId) o;
        return Objects.equals(event, that.event) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, user);
    }
}
