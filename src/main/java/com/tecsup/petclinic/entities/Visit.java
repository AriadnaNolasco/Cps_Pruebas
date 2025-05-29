package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity(name = "visits")
@NoArgsConstructor
@Data
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "visit_date", nullable = false)
    private Date visitDate;

    @Column(nullable = false)
    private String description;

    // Constructor con ID (opcional para pruebas o actualizaciones)
    public Visit(Integer id, Integer petId, Date visitDate, String description) {
        this.id = id;
        this.petId = petId;
        this.visitDate = visitDate;
        this.description = description;
    }

    // Constructor sin ID
    public Visit(Integer petId, Date visitDate, String description) {
        this.petId = petId;
        this.visitDate = visitDate;
        this.description = description;
    }
}
