package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.Date;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
    List<Visit> findByPetId(Integer petId);
    List<Visit> findByVisitDate(Date visitDate);
    List<Visit> findByDescriptionContaining(String keyword);
}
