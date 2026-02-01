package com.archi.microservice.shared.domain.events;

import java.time.LocalDateTime;

public interface DomainEvent {
    String getAggregateType();

    Object getAggregateId();

    LocalDateTime getOccurredAt();
}
