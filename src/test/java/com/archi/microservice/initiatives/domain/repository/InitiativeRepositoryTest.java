package com.archi.microservice.initiatives.domain.repository;

import com.archi.microservice.initiatives.domain.aggregates.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitiativeRepositoryTest {
    @Mock
    private InitiativeRepository repository;

    private Initiative initiative;

    @BeforeEach
    void setUp() {
        initiative = Initiative.create("CODE-001", "Título", "Descripción", 1L, 2L);
    }

    // Happy Path: save debe guardar la iniciativa
    @Test
    void shouldSaveInitiative_WhenValid() {
        doNothing().when(repository).save(any(Initiative.class));
        repository.save(initiative);
        verify(repository).save(initiative);
    }

    // Happy Path: findById retorna iniciativa existente
    @Test
    void shouldReturnInitiative_WhenFindByIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(initiative));
        Optional<Initiative> result = repository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("CODE-001", result.get().getCode());
    }

    // Edge Case: findById retorna vacío si no existe
    @Test
    void shouldReturnEmpty_WhenFindByIdNotExists() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        Optional<Initiative> result = repository.findById(99L);
        assertTrue(result.isEmpty());
    }

    // Happy Path: findByCode retorna iniciativa
    @Test
    void shouldReturnInitiative_WhenFindByCodeExists() {
        when(repository.findByCode("CODE-001")).thenReturn(Optional.of(initiative));
        Optional<Initiative> result = repository.findByCode("CODE-001");
        assertTrue(result.isPresent());
        assertEquals("CODE-001", result.get().getCode());
    }

    // Edge Case: findByCode retorna vacío si no existe
    @Test
    void shouldReturnEmpty_WhenFindByCodeNotExists() {
        when(repository.findByCode("NOPE")).thenReturn(Optional.empty());
        Optional<Initiative> result = repository.findByCode("NOPE");
        assertTrue(result.isEmpty());
    }

    // Happy Path: findByStatus retorna lista
    @Test
    void shouldReturnInitiatives_WhenFindByStatus() {
        List<Initiative> initiatives = List.of(initiative);
        when(repository.findByStatus(InitiativeStatus.DRAFT)).thenReturn(initiatives);
        List<Initiative> result = repository.findByStatus(InitiativeStatus.DRAFT);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    // Happy Path: findByArchitectId retorna lista
    @Test
    void shouldReturnInitiatives_WhenFindByArchitectId() {
        List<Initiative> initiatives = List.of(initiative);
        when(repository.findByArchitectId(1L)).thenReturn(initiatives);
        List<Initiative> result = repository.findByArchitectId(1L);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    // Happy Path: delete debe eliminar la iniciativa
    @Test
    void shouldDeleteInitiative_WhenValid() {
        doNothing().when(repository).delete(any(Initiative.class));
        repository.delete(initiative);
        verify(repository).delete(initiative);
    }
}
