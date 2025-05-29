package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    /**
     * Test de creación de visita
     */
    @Test
    public void testCreateVisit() {
        Integer petId = 1;
        Date visitDate = Date.valueOf(LocalDate.now());
        String description = "Consulta general";

        Visit visit = new Visit(petId, visitDate, description);

        Visit createdVisit = visitService.create(visit);

        assertNotNull(createdVisit.getId());
        assertEquals(petId, createdVisit.getPetId());
        assertEquals(visitDate, createdVisit.getVisitDate());
        assertEquals(description, createdVisit.getDescription());
    }

    /**
     * Test de actualización de visita
     */
    @Test
    public void testUpdateVisit() {
        // Crear una visita
        Visit visit = new Visit(1, Date.valueOf("2024-05-01"), "Visita inicial");
        Visit createdVisit = visitService.create(visit);

        // Modificar datos
        createdVisit.setVisitDate(Date.valueOf("2024-06-01"));
        createdVisit.setDescription("Visita actualizada");

        // Actualizar
        Visit updatedVisit = visitService.update(createdVisit);

        assertEquals("Visita actualizada", updatedVisit.getDescription());
        assertEquals(Date.valueOf("2024-06-01"), updatedVisit.getVisitDate());
    }

    /**
     * Test de búsqueda por ID
     */
    @Test
    public void testFindVisitById() {
        Visit visit = new Visit(1, Date.valueOf("2024-04-15"), "Chequeo dental");
        Visit createdVisit = visitService.create(visit);

        try {
            Visit foundVisit = visitService.findById(createdVisit.getId());

            assertEquals(createdVisit.getId(), foundVisit.getId());
            assertEquals("Chequeo dental", foundVisit.getDescription());
        } catch (VisitNotFoundException e) {
            fail("No se encontró la visita: " + e.getMessage());
        }
    }

    /**
     * Test de eliminación de visita
     */
    @Test
    public void testDeleteVisit() {
        Visit visit = new Visit(1, Date.valueOf("2024-04-10"), "Vacunación");
        Visit createdVisit = visitService.create(visit);

        try {
            visitService.delete(createdVisit.getId());
        } catch (VisitNotFoundException e) {
            fail("No se pudo eliminar la visita: " + e.getMessage());
        }

        assertThrows(VisitNotFoundException.class, () -> {
            visitService.findById(createdVisit.getId());
        });
    }
}
