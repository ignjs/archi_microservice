package com.archi.microservice.initiatives.domain.entities;

import java.time.LocalDateTime;
import com.archi.microservice.shared.domain.exception.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private Long id;
    private String reviewerName;
    private String reviewerEmail;
    private String comments;
    private Integer rating;
    private ReviewStatus status;
    private LocalDateTime reviewedAt;

    // Ejemplo de validación de negocio
    public void approve() {
        if (this.status != ReviewStatus.PENDING) {
            throw new BusinessRuleException("Solo se pueden aprobar revisiones en estado PENDING");
        }
        this.status = ReviewStatus.APPROVED;
    }

    public void reject(String reason) {
        if (this.status != ReviewStatus.PENDING) {
            throw new BusinessRuleException("Solo se pueden rechazar revisiones en estado PENDING");
        }
        this.status = ReviewStatus.REJECTED;
        this.comments = (this.comments == null ? "" : this.comments + "\n") + "Rechazo: " + reason;
    }
}