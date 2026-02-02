package com.archi.microservice.shared.domain.events;

public class InitiativeCreatedEvent extends BaseDomainEvent {
    private String code;
    private String title;

    public InitiativeCreatedEvent(String code, String title) {
        this.code = code;
        this.title = title;
    }

    @Override
    public String getAggregateType() {
        return "Initiative";
    }

    @Override
    public Object getAggregateId() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
}
