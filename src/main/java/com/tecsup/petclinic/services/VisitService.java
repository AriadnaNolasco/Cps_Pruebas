package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.sql.Date;
import java.util.List;

public interface VisitService {
    /**
     * Crea una nueva visita
     *
     * @param visit
     * @return
     */
    Visit create(Visit visit);

    /**
     * Actualiza una visita existente
     *
     * @param visit
     * @return
     */
    Visit update(Visit visit);

    /**
     * Elimina una visita por su ID
     *
     * @param id
     * @throws VisitNotFoundException
     */
    void delete(Long id) throws VisitNotFoundException;

    /**
     * Busca una visita por su ID
     *
     * @param id
     * @return
     * @throws VisitNotFoundException
     */
    Visit findById(Long id) throws VisitNotFoundException;

    /**
     * Busca visitas por el ID de la mascota
     *
     * @param petId
     * @return
     */
    List<Visit> findByPetId(Long petId);

    /**
     * Busca visitas por la fecha de la visita
     *
     * @param visitDate
     * @return
     */
    List<Visit> findByVisitDate(Date visitDate);

    /**
     * Lista todas las visitas
     *
     * @return
     */
    List<Visit> findAll();
}
