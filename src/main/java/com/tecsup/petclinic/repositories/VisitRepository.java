package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.Date;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
    // Buscar visitas por ID de mascota
    List<Visit> findByPetId(Long petId);

    // Buscar visitas por fecha exacta
    List<Visit> findByVisitDate(Date visitDate);

    // Buscar visitas por descripción (si se desea)
    List<Visit> findByDescriptionContaining(String keyword);

    @Override
    List<Visit> findAll();
}
