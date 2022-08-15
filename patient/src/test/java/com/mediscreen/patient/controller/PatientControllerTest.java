package com.mediscreen.patient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mediscreen.patient.model.Patient;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("PatientControllerTest")
@DisplayName("Patient controller test logic")
class PatientControllerTest extends AbstractTest {

    private final String uri = "/patient";

    @Test
    @DisplayName("getAllPatient should return map of String Object data given page number")
    void getAllPatient_ShouldReturnMapOfStringObject_ForGivenPageNumber() throws Exception {
        // GIVEN

        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri + "/getAll/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Map<String, Object> data = super.mapFromJson(content, Map.class);

        // THEN
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(data.size() > 0);
        assertThat(data, IsMapContaining.hasEntry("patients", data.get("patients")));
        assertThat(data, IsMapContaining.hasEntry("totalPages", 2));
    }

    @Test
    @DisplayName("getPatient should return 404 status code for given invalid patient id")
    void getPatient_ShouldReturn400StatusCode_ForGivenPatientInvalidId() throws Exception {
        // GIVEN

        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri + "/get/1000")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        // THEN
        assertEquals(400, mvcResult.getResponse().getStatus());

    }


    @Test
    @DisplayName("getPatient should return patient for given patient id")
    void getPatient_ShouldReturnPatient_ForGivenPatientId() throws Exception {
        // GIVEN

        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri + "/get/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Patient patient = super.mapFromJson(content, Patient.class);

        // THEN
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(patient.getFamily(), "TestNone");
        assertEquals(patient.getGiven(), "Test");
        assertEquals(patient.getSex(), "F");

    }

    @Test
    @DisplayName("updatePatient should return 400 status code validation error for given empty field")
    void updatePatient_ShouldReturn400StatusCodeValidationError_ForGivenEmptyField() throws Exception {
        // GIVEN
        Patient patient =   new Patient(1000L, "", "",
                new SimpleDateFormat("yyyy-MM-dd").parse("2202-12-25"),
                "M", "7 rue Lucien Deneau", "444-555-6666");
        String inputJson = super.mapToJson(patient);

        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.put(uri + "/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    @DisplayName("updatePatient should return 400 status code for given not exist patient")
    void updatePatient_ShouldReturn400StatusCode_ForGivenNotExistPatient() throws Exception {
        // GIVEN
        Patient patient =   new Patient(1000L, "TestBold", "Test",
                new SimpleDateFormat("yyyy-MM-dd").parse("2202-12-25"),
                "M", "7 rue Lucien Deneau", "444-555-6666");
        String inputJson = super.mapToJson(patient);

        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.put(uri + "/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    @DisplayName("updatePatient should return updated patient for given patient")
    void updatePatient_ShouldReturnUpdatedPatient_ForGivenPatient() throws Exception {
        // GIVEN
        Patient patient =   new Patient(1L, "TestBold", "Test",
                new SimpleDateFormat("yyyy-MM-dd").parse("2202-12-25"),
                "M", "7 rue Lucien Deneau", "444-555-6666");
        String inputJson = super.mapToJson(patient);

        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.put(uri + "/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.family").value("TestBold"))
                .andExpect(jsonPath("$.given").value("Test"))
                .andReturn();
    }

    @Test
    @DisplayName("addPatient should return 400 status code validation error for given empty field")
    void addPatient_ShouldReturn400StatusCodeValidationError_ForGivenEmptyField() throws Exception {
        // GIVEN
        Patient patient =   new Patient(8L, "", "",
                new SimpleDateFormat("yyyy-MM-dd").parse("2202-12-25"),
                "M", "7 rue Lucien Deneau", "444-555-6666");
        String inputJson = super.mapToJson(patient);

        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.post(uri + "/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    @DisplayName("addPatient should return added patient for given patient")
    void addPatient_ShouldReturnAddedPatient_ForGivenPatient() throws Exception {
        // GIVEN
        Patient patient =   new Patient(8L, "TestRed", "Test",
                new SimpleDateFormat("yyyy-MM-dd").parse("2202-12-25"),
                "M", "7 rue Lucien Deneau", "444-555-6666");
        String inputJson = super.mapToJson(patient);

        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.post(uri + "/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.family").value("TestRed"))
                .andExpect(jsonPath("$.given").value("Test"))
                .andReturn();
    }

    @Test
    @DisplayName("deletePatient should delete patient for given patient id")
    void deletePatient_ShouldReturn400StatusCode_ForGivenNotExistPatientId() throws Exception {
        // GIVEN

        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete(uri + "/delete/1000")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        // THEN
        assertEquals(400, mvcResult.getResponse().getStatus());

    }

    @Test
    @DisplayName("deletePatient should delete patient for given patient id")
    void deletePatient_ShouldDeletePatient_ForGivenPatientId() throws Exception {
        // GIVEN
        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete(uri + "/delete/4")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        // THEN
        assertEquals(204, mvcResult.getResponse().getStatus());
    }
}