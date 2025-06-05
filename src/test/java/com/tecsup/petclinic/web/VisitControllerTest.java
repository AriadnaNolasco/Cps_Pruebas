package com.tecsup.petclinic.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateVisit() throws Exception {

        String DESCRIPTION = "Routine Check";
        Integer PET_ID = 1;
        String VISIT_DATE = "2025-06-05";

        VisitDTO newVisitDTO = new VisitDTO();
        newVisitDTO.setDescription(DESCRIPTION);
        newVisitDTO.setPetId(PET_ID);
        newVisitDTO.setVisitDate(java.sql.Date.valueOf(VISIT_DATE));

        this.mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.petId", is(PET_ID)))
                .andExpect(jsonPath("$.visitDate", is(VISIT_DATE)));
    }

    @Test
    public void testUpdateVisit() throws Exception {
        // Visita creada
        String originalDescription = "Initial Check";
        Integer petId = 1;
        String originalDate = "2025-06-01";

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setDescription(originalDescription);
        visitDTO.setPetId(petId);
        visitDTO.setVisitDate(java.sql.Date.valueOf(originalDate));

        String response = this.mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(visitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Paso 2: Obtener el ID del JSON de respuesta
        VisitDTO createdVisit = om.readValue(response, VisitDTO.class);
        Integer visitId = createdVisit.getId();

        // Paso 3: Preparar datos actualizados
        String updatedDescription = "Updated Check";
        String updatedDate = "2025-06-10";

        VisitDTO updatedVisitDTO = new VisitDTO();
        updatedVisitDTO.setId(visitId);
        updatedVisitDTO.setPetId(petId); // mismo petId
        updatedVisitDTO.setVisitDate(java.sql.Date.valueOf(updatedDate));
        updatedVisitDTO.setDescription(updatedDescription);

        // Paso 4: Ejecutar PUT /visits/{id}
        this.mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/visits/" + visitId)
                                .content(om.writeValueAsString(updatedVisitDTO))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(visitId)))
                .andExpect(jsonPath("$.description", is(updatedDescription)))
                .andExpect(jsonPath("$.visitDate", is(updatedDate)))
                .andExpect(jsonPath("$.petId", is(petId)));
    }

}
