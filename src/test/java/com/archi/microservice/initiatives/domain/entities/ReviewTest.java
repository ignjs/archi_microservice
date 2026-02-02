package com.archi.microservice.initiatives.domain.entities;

import com.archi.microservice.shared.domain.exception.BusinessRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    private Review review;

    @BeforeEach
    void setUp() {
        review = Review.builder()
                .id(1L)
                .reviewerName("QA Senior")
                .reviewerEmail("qa@archi.com")
                .comments("Initial comment")
                .rating(5)
                .status(ReviewStatus.PENDING)
                .build();
    }

    // Happy Path: aprobar revisión en estado PENDING
    @Test
    void shouldApproveReview_WhenStatusIsPending() {
        review.approve();
        assertEquals(ReviewStatus.APPROVED, review.getStatus());
    }

    // Error: aprobar revisión en estado no PENDING
    @Test
    void shouldThrowBusinessRuleException_WhenApproveAndStatusNotPending() {
        review.setStatus(ReviewStatus.REJECTED);
        assertThrows(BusinessRuleException.class, review::approve);
    }

    // Happy Path: rechazar revisión en estado PENDING
    @Test
    void shouldRejectReview_WhenStatusIsPending() {
        review.reject("No cumple requisitos");
        assertEquals(ReviewStatus.REJECTED, review.getStatus());
        assertTrue(review.getComments().contains("Rechazo: No cumple requisitos"));
    }

    // Error: rechazar revisión en estado no PENDING
    @Test
    void shouldThrowBusinessRuleException_WhenRejectAndStatusNotPending() {
        review.setStatus(ReviewStatus.APPROVED);
        assertThrows(BusinessRuleException.class, () -> review.reject("Motivo"));
    }
}
