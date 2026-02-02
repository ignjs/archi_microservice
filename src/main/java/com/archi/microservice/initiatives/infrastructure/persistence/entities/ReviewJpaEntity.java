package com.archi.microservice.initiatives.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "reviews", schema = "initiative_mgmt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewer_name", nullable = false, length = 100)
    private String reviewerName;

    @Column(name = "reviewer_email", length = 100)
    private String reviewerEmail;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiative_id", nullable = false)
    private InitiativeJpaEntity initiative;

    @Column(name = "reviewed_at", nullable = false)
    private LocalDateTime reviewedAt;
}