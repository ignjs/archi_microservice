package com.archi.microservice.initiatives.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Table(name = "components", schema = "initiative_mgmt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component_code", unique = true, nullable = false, length = 50)
    private String componentCode;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "estimated_cost", precision = 12, scale = 2)
    private BigDecimal estimatedCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiative_id", nullable = false)
    private InitiativeJpaEntity initiative;
}