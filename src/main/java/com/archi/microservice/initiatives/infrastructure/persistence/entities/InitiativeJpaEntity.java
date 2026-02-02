package com.archi.microservice.initiatives.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.archi.microservice.initiatives.domain.aggregates.InitiativeStatus;

import lombok.*;

@Entity
@Table(name = "initiatives", schema = "initiative_mgmt", indexes = {
    @Index(name = "idx_initiatives_code", columnList = "code"),
    @Index(name = "idx_initiatives_status", columnList = "status"),
    @Index(name = "idx_initiatives_architect_id", columnList = "architect_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiativeJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "code", unique = true, nullable = false, length = 50)
    private String code;
    
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InitiativeStatus status;
    
    @Column(name = "architect_id", nullable = false)
    private Long architectId;
    
    @Column(name = "dev_team_id", nullable = false)
    private Long teamId;
    
    @Column(name = "business_owner_name")
    private String businessOwnerName;
    
    @Column(name = "business_owner_email")
    private String businessOwnerEmail;
    
    @Column(name = "expected_start_date")
    private LocalDate expectedStartDate;
    
    @Column(name = "expected_end_date")
    private LocalDate expectedEndDate;
    
    @Column(name = "estimated_cost", precision = 12, scale = 2)
    private BigDecimal estimatedCost;
    
    @Column(name = "priority_level", length = 20)
    private String priorityLevel;
    
    @OneToMany(mappedBy = "initiative", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewJpaEntity> reviews = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}