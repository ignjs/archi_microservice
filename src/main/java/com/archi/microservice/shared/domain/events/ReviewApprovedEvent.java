package com.archi.microservice.shared.domain.events;

public class ReviewApprovedEvent extends BaseDomainEvent {
    private Long reviewId;
    private String reviewerComments;

    public ReviewApprovedEvent(Long reviewId, String reviewerComments) {
        this.reviewId = reviewId;
        this.reviewerComments = reviewerComments;
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

    public String getReviewerComments() {
        return reviewerComments;
    }
}
