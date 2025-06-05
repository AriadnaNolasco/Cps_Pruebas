package com.tecsup.petclinic.web;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.services.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Visit createVisit(@RequestBody Visit visit) {
        return visitService.create(visit);
    }

    @GetMapping
    public List<Visit> getAllVisits() {
        return visitService.findAll();
    }
    @GetMapping("/{id}")
    public Visit getVisitById(@PathVariable Integer id) throws VisitNotFoundException {
        return visitService.findById(id);
    }

    @PutMapping("/{id}")
    public Visit updateVisit(@RequestBody Visit visit, @PathVariable Integer id) throws VisitNotFoundException {
        Visit existingVisit = visitService.findById(id);

        existingVisit.setVisitDate(visit.getVisitDate());
        existingVisit.setDescription(visit.getDescription());
        existingVisit.setPetId(visit.getPetId());

        return visitService.update(existingVisit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVisit(@PathVariable Integer id) throws VisitNotFoundException {
        visitService.delete(id);
    }

    @GetMapping("/pet/{petId}")
    public List<Visit> getVisitsByPetId(@PathVariable Integer petId) {
        return visitService.findByPetId(petId);
    }
}

