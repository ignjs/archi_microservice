package com.archi.microservice.initiatives.domain.aggregates;

import com.archi.microservice.shared.domain.aggregates.BaseAggregateRoot;
import com.archi.microservice.shared.domain.events.*;
import com.archi.microservice.initiatives.domain.entities.*;
import com.archi.microservice.shared.domain.exception.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Initiative extends BaseAggregateRoot {
    private String code;
    private String title;
    private String description;
    private InitiativeStatus status;
    private Long architectId;
    private Long teamId;
    private String businessOwnerName;
    private String businessOwnerEmail;
    private LocalDate expectedStartDate;
    private LocalDate expectedEndDate;
    private BigDecimal estimatedCost;
    private String priorityLevel;
    private List<Review> reviews = new ArrayList<>();
    
    // Constructor privado (usamos factory method)
    private Initiative() {}
    
    // Factory method
    public static Initiative create(String code, String title, String description, 
                                    Long architectId, Long teamId) {
        if (code == null || code.isEmpty()) {
            throw new ValidationException("Code is required");
        }
        if (title == null || title.isEmpty()) {
            throw new ValidationException("Title is required");
        }
        if (title.length() > 255) {
            throw new ValidationException("Title max 255 characters");
        }
        if (description == null || description.isEmpty()) {
            throw new ValidationException("Description is required");
        }
        if (architectId == null || architectId <= 0) {
            throw new ValidationException("Valid architectId is required");
        }
        if (teamId == null || teamId <= 0) {
            throw new ValidationException("Valid teamId is required");
        }
        
        Initiative initiative = new Initiative();
        initiative.code = code;
        initiative.title = title;
        initiative.description = description;
        initiative.status = InitiativeStatus.DRAFT;
        initiative.architectId = architectId;
        initiative.teamId = teamId;
        initiative.priorityLevel = "MEDIUM";
        
        // Domain event
        initiative.addDomainEvent(new InitiativeCreatedEvent(code, title));
        
        return initiative;
    }
    
    public void submitForReview(String completionNotes) throws BusinessRuleException {
        if (this.status != InitiativeStatus.IN_PROGRESS) {
            throw new BusinessRuleException("Only IN_PROGRESS initiatives can be submitted for review");
        }
        
        this.status = InitiativeStatus.IN_REVIEW;
        this.addDomainEvent(new InitiativeSubmittedEvent(this.code, completionNotes));
    }
    
    public void approve() throws BusinessRuleException {
        boolean allReviewsApproved = reviews.stream()
            .allMatch(r -> r.getStatus() == ReviewStatus.APPROVED);
        
        if (!allReviewsApproved) {
            throw new BusinessRuleException("All reviews must be approved before completing");
        }
        
        this.status = InitiativeStatus.APPROVED;
        this.addDomainEvent(new InitiativeApprovedEvent(this.code));
    }
    
    // Getters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public InitiativeStatus getStatus() { return status; }
    public Long getArchitectId() { return architectId; }
    public Long getTeamId() { return teamId; }
    public List<Review> getReviews() { return new ArrayList<>(reviews); }
    
    // Setters (mínimos)
    public void setStatus(InitiativeStatus status) { this.status = status; }
    public void addReview(Review review) { this.reviews.add(review); }
}