package com.tecsup.petclinic.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

        // Valores iniciales
        String DESCRIPTION = "Initial Check";
        Integer PET_ID = 1;
        String VISIT_DATE = "2025-06-01";

        // Nuevos valores para actualización
        String UP_DESCRIPTION = "Updated Check";
        String UP_VISIT_DATE = "2025-06-10";

        // Crear visita original
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setDescription(DESCRIPTION);
        visitDTO.setPetId(PET_ID);
        visitDTO.setVisitDate(java.sql.Date.valueOf(VISIT_DATE));

        // CREATE
        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(visitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        // Obtener ID de la respuesta
        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // Preparar datos actualizados
        VisitDTO updatedVisitDTO = new VisitDTO();
        updatedVisitDTO.setId(id);
        updatedVisitDTO.setDescription(UP_DESCRIPTION);
        updatedVisitDTO.setPetId(PET_ID); // se mantiene igual
        updatedVisitDTO.setVisitDate(java.sql.Date.valueOf(UP_VISIT_DATE));

        // UPDATE
        mockMvc.perform(put("/visits/" + id)
                        .content(om.writeValueAsString(updatedVisitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.description", is(UP_DESCRIPTION)))
                .andExpect(jsonPath("$.visitDate", is(UP_VISIT_DATE)))
                .andExpect(jsonPath("$.petId", is(PET_ID)));
    }

}
