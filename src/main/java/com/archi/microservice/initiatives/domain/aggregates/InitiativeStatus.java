package com.archi.microservice.initiatives.domain.aggregates;

public enum InitiativeStatus {
    DRAFT,
    DEV_PENDING,
    IN_PROGRESS,
    COMPLETED,
    IN_REVIEW,
    CHANGES_REQUESTED,
    APPROVED,
    REJECTED,
    CANCELLED
}
