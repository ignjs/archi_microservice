package com.archi.microservice.initiatives.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preliminary_info", schema = "initiative_mgmt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreliminaryInfoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "risk_assessment", columnDefinition = "TEXT")
    private String riskAssessment;

    @Column(name = "opportunity", columnDefinition = "TEXT")
    private String opportunity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiative_id", nullable = false)
    private InitiativeJpaEntity initiative;
}