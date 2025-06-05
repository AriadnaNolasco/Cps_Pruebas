package com.tecsup.petclinic.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.services.VisitService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitService visitService;

    @BeforeEach
    void setUp() {

        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testCreateVisit() throws Exception {

        String description = "Routine Check";

        VisitDTO newVisitDTO = new VisitDTO();
        newVisitDTO.setDescription(description);

        Visit newVisit = new Visit();
        newVisit.setDescription(description);

        Mockito.when(visitService.create(newVisit))
                .thenReturn(newVisit);

        mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is(description)));
    }

    @Test
    public void testUpdateVisit() throws Exception {

        Integer visitId = 1;
        String updatedDescription = "Updated Check";

        VisitDTO updatedVisitDTO = new VisitDTO();
        updatedVisitDTO.setId(visitId);
        updatedVisitDTO.setDescription(updatedDescription);

        Visit existingVisit = new Visit();
        existingVisit.setId(visitId);
        existingVisit.setDescription("Old Description");

        Visit updatedVisit = new Visit();
        updatedVisit.setId(visitId);
        updatedVisit.setDescription(updatedDescription);

        // Simula que el servicio encuentra el registro existente
        Mockito.when(visitService.findById(visitId)).thenReturn(existingVisit);

        // Simula que el servicio actualiza el registro
        Mockito.when(visitService.update(Mockito.any(Visit.class))).thenReturn(updatedVisit);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .put("/visits/{id}", visitId)
                                .content(om.writeValueAsString(updatedVisitDTO))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(updatedDescription)));
    }

    @Test
    public void testSearchVisit() throws Exception {
        Integer visitId = 1;
        String description = "Routine Check";

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setId(visitId);
        visitDTO.setDescription(description);

        Visit visit = new Visit();
        visit.setId(visitId);
        visit.setDescription(description);

        Mockito.when(visitService.findById(visitId)).thenReturn(visit);

        mockMvc.perform(get("/visits/{id}", visitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(visitId)))
                .andExpect(jsonPath("$.description", is(description)));
    }

}
