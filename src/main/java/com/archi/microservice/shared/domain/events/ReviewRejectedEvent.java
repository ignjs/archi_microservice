package com.archi.microservice.shared.domain.events;

public class ReviewRejectedEvent extends BaseDomainEvent {
    private Long reviewId;
    private String rejectionReason;

    public ReviewRejectedEvent(Long reviewId, String rejectionReason) {
        this.reviewId = reviewId;
        this.rejectionReason = rejectionReason;
    }

    @Override
    public String getAggregateType() {
        return "Review";
    }

    @Override
    public Object getAggregateId() {
        return reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }
}
