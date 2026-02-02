package com.archi.microservice.shared.domain.events;

public class InitiativeApprovedEvent extends BaseDomainEvent {
    private String code;

    public InitiativeApprovedEvent(String code) {
        this.code = code;
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
}
