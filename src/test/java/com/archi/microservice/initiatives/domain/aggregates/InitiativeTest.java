package com.archi.microservice.initiatives.domain.aggregates;

import com.archi.microservice.initiatives.domain.entities.*;
import com.archi.microservice.shared.domain.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InitiativeTest {
    private Initiative initiative;

    @BeforeEach
    void setUp() {
        initiative = Initiative.create(
                "CODE-001",
                "Título",
                "Descripción válida",
                1L,
                2L
        );
    }

    // Happy Path: Creación exitosa
    @Test
    void shouldCreateInitiative_WhenDataIsValid() {
        assertNotNull(initiative);
        assertEquals("CODE-001", initiative.getCode());
        assertEquals("Título", initiative.getTitle());
        assertEquals(InitiativeStatus.DRAFT, initiative.getStatus());
    }

    // Edge Case: Código vacío
    @Test
    void shouldThrowValidationException_WhenCodeIsEmpty() {
        assertThrows(ValidationException.class, () ->
                Initiative.create("", "Título", "Desc", 1L, 2L)
        );
    }

    // Edge Case: Título nulo
    @Test
    void shouldThrowValidationException_WhenTitleIsNull() {
        assertThrows(ValidationException.class, () ->
                Initiative.create("CODE-002", null, "Desc", 1L, 2L)
        );
    }

    // Edge Case: Título demasiado largo
    @Test
    void shouldThrowValidationException_WhenTitleTooLong() {
        String longTitle = "x".repeat(256);
        assertThrows(ValidationException.class, () ->
                Initiative.create("CODE-003", longTitle, "Desc", 1L, 2L)
        );
    }

    // Edge Case: Descripción vacía
    @Test
    void shouldThrowValidationException_WhenDescriptionIsEmpty() {
        assertThrows(ValidationException.class, () ->
                Initiative.create("CODE-004", "Título", "", 1L, 2L)
        );
    }

    // Edge Case: architectId inválido
    @Test
    void shouldThrowValidationException_WhenArchitectIdInvalid() {
        assertThrows(ValidationException.class, () ->
                Initiative.create("CODE-005", "Título", "Desc", null, 2L)
        );
        assertThrows(ValidationException.class, () ->
                Initiative.create("CODE-005", "Título", "Desc", 0L, 2L)
        );
    }

    // Edge Case: teamId inválido
    @Test
    void shouldThrowValidationException_WhenTeamIdInvalid() {
        assertThrows(ValidationException.class, () ->
                Initiative.create("CODE-006", "Título", "Desc", 1L, null)
        );
        assertThrows(ValidationException.class, () ->
                Initiative.create("CODE-006", "Título", "Desc", 1L, 0L)
        );
    }

    // Happy Path: submitForReview solo si IN_PROGRESS
    @Test
    void shouldChangeStatusToInReview_WhenSubmitForReviewAndStatusIsInProgress() {
        initiative.setStatus(InitiativeStatus.IN_PROGRESS);
        initiative.submitForReview("Notas de finalización");
        assertEquals(InitiativeStatus.IN_REVIEW, initiative.getStatus());
    }

    // Error: submitForReview en estado incorrecto
    @Test
    void shouldThrowBusinessRuleException_WhenSubmitForReviewAndStatusNotInProgress() {
        initiative.setStatus(InitiativeStatus.DRAFT);
        assertThrows(BusinessRuleException.class, () ->
                initiative.submitForReview("Notas")
        );
    }

    // Happy Path: approve solo si todos los reviews están aprobados
    @Test
    void shouldApproveInitiative_WhenAllReviewsApproved() {
        initiative.setStatus(InitiativeStatus.IN_PROGRESS);
        initiative.addReview(Review.builder().status(ReviewStatus.APPROVED).build());
        initiative.addReview(Review.builder().status(ReviewStatus.APPROVED).build());
        initiative.submitForReview("Notas");
        initiative.approve();
        assertEquals(InitiativeStatus.APPROVED, initiative.getStatus());
    }

    // Error: approve con reviews no aprobados
    @Test
    void shouldThrowBusinessRuleException_WhenNotAllReviewsApproved() {
        initiative.setStatus(InitiativeStatus.IN_PROGRESS);
        initiative.addReview(Review.builder().status(ReviewStatus.APPROVED).build());
        initiative.addReview(Review.builder().status(ReviewStatus.REJECTED).build());
        initiative.submitForReview("Notas");
        assertThrows(BusinessRuleException.class, () ->
                initiative.approve()
        );
    }
}
