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
}

