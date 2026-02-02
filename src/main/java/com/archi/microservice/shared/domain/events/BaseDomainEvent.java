package com.archi.microservice.shared.domain.events;

import java.time.LocalDateTime;

public abstract class BaseDomainEvent implements DomainEvent {
    private LocalDateTime occurredAt = LocalDateTime.now();

    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}