package com.mediscreen.assessment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.model.Patient;
import com.mediscreen.assessment.web.service.AssessmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("AssessmentControllerTest")
@DisplayName("Assessment controller test logic")
@SpringBootTest
@AutoConfigureMockMvc
class AssessmentControllerTest extends AbstractTest{

    @MockBean
    private AssessmentService assessmentService;


    @Test
    @DisplayName("getAssessment, should return assessment for given patient")
    void getAssessment_ShouldReturnAssessment_ForGivenPatient() throws Exception {
        // GIVEN
        Patient patient =   new Patient(8L, "Cartman", "Eric",
                new SimpleDateFormat("yyyy-MM-dd").parse("2202-12-25"),
                "M", "7 rue Lucien Deneau", "444-555-6666");

        when(assessmentService.getPatientAssessment(any(Patient.class))).thenReturn(new Assessment(
                "Cartman", "Eric", 41, "Aucun risque (None)"
        ));

        String inputJson = super.mapToJson(patient);
        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.post( "/assess")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.family").value("Cartman"))
                .andExpect(jsonPath("$.given").value("Eric"))
                .andExpect(jsonPath("$.birthdate").value("41"))
                .andExpect(jsonPath("$.assessment").value("Aucun risque (None)"))
                .andReturn();

    }
}