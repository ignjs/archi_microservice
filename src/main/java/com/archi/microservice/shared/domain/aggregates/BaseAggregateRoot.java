package com.archi.microservice.shared.domain.aggregates;

import java.util.ArrayList;
import java.util.List;

import com.archi.microservice.shared.domain.events.DomainEvent;


public abstract class BaseAggregateRoot {
    private List<DomainEvent> domainEvents = new ArrayList<>();

    protected void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
