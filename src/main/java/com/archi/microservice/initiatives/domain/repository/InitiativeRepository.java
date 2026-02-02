package com.archi.microservice.initiatives.domain.repository;

import com.archi.microservice.initiatives.domain.aggregates.Initiative;
import com.archi.microservice.initiatives.domain.aggregates.InitiativeStatus;

import java.util.Optional;
import java.util.List;

public interface InitiativeRepository {
    void save(Initiative initiative);
    Optional<Initiative> findById(Long id);
    Optional<Initiative> findByCode(String code);
    List<Initiative> findByStatus(InitiativeStatus status);
    List<Initiative> findByArchitectId(Long architectId);
    void delete(Initiative initiative);
}
