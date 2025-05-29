package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.sql.Date;
import java.util.List;

public interface VisitService {
    Visit create(Visit visit);
    Visit update(Visit visit);
    void delete(Integer id) throws VisitNotFoundException;
    Visit findById(Integer id) throws VisitNotFoundException;
    List<Visit> findByPetId(Integer petId);
    List<Visit> findByVisitDate(Date visitDate);
    List<Visit> findAll();
}
