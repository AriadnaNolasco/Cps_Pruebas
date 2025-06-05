package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VisitDTO {

    private Integer id;
    private Integer petId;
    private Date visitDate;
    private String description;

}
