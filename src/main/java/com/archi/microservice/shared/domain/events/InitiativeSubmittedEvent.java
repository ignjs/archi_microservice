package com.archi.microservice.shared.domain.events;

public class InitiativeSubmittedEvent extends BaseDomainEvent {
    private String code;
    private String completionNotes;

    public InitiativeSubmittedEvent(String code, String completionNotes) {
        this.code = code;
        this.completionNotes = completionNotes;
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

    public String getCompletionNotes() {
        return completionNotes;
    }
}
