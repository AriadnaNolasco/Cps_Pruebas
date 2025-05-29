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
    private int id;

    @Column(name = "pet_id", nullable = false)
    private int petId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "visit_date", nullable = false)
    private Date visitDate;

    @Column(nullable = false)
    private String description;

    // Constructor con ID (útil para pruebas o actualizaciones)
    public Visit(int id, int petId, Date visitDate, String description) {
        this.id = id;
        this.petId = petId;
        this.visitDate = visitDate;
        this.description = description;
    }

    // Constructor sin ID (útil para creaciones)
    public Visit(int petId, Date visitDate, String description) {
        this.petId = petId;
        this.visitDate = visitDate;
        this.description = description;
    }
}
