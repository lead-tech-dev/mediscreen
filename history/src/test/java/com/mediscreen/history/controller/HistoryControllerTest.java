package com.mediscreen.history.controller;

import com.mediscreen.history.model.History;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("HistoryControllerTest")
@DisplayName("History controller test logic")
class HistoryControllerTest extends  AbstractTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String uri = "/history";

    @Test
    @DisplayName("getAllHistory should return patient history given patient id")
    void getAllHistory_ShouldReturnPatientHistoryList_ForGivenPatientId() throws Exception {
        // GIVEN
        // GIVEN

        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri + "/getAll/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        List data = super.mapFromJson(content, List.class);

        // THEN
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(data.size() > 0);
    }

    @Test
    @DisplayName("getHistory should return 404 status code for given invalid history id")
    void getHistory_ShouldReturn400StatusCode_ForGivenHistoryInvalidId() throws Exception {
        // GIVEN
        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri + "/get/1000")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        // THEN
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("getHistory should return history for given history id")
    void getHistory_ShouldReturnPatient_ForGivenHistoryId() throws Exception {
        // GIVEN
        String historyId = "6177a31824f1d205e0b0692d";
        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get(uri + "/get/" + historyId)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        History history = super.mapFromJson(content, History.class);

        // THEN
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(history.getPatientId(), 1);
        assertEquals(history.getId(), historyId);
        assertEquals(history.getNote(), "Patient states that they are 'feeling terrific' Weight at or below recommended level");
    }

    @Test
    @DisplayName("addHistory should return 400 status code validation error for given empty field")
    void addHistory_ShouldReturn400StatusCodeValidationError_ForGivenEmptyField() throws Exception {
        // GIVEN
        History history = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "");

        String inputJson = super.mapToJson(history);

        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.post(uri + "/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    @DisplayName("addHistory should return added history for given history")
    void addHistory_ShouldReturnAddedHistory_ForGivenHistory() throws Exception {
        // GIVEN
        History history = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        String inputJson = super.mapToJson(history);

        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.post(uri + "/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.note").value("Patient states that they are a short term Smoker Hemoglobin A1C above recommended level"))
                .andReturn();
    }

    @Test
    @DisplayName("updateHistory should return 400 status code validation error for given empty field")
    void updateHistory_ShouldReturn400StatusCodeValidationError_ForGivenEmptyField() throws Exception {
        // GIVEN
        History history = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "");

        // WHEN
        // THEN
        String inputJson = super.mapToJson(history);

        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.put(uri + "/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    @DisplayName("updateHistory should return 400 status code for given not exist history")
    void updateHistory_ShouldReturn400StatusCode_ForGivenNotExistHistory() throws Exception {
        // GIVEN
        History history = new History(
                "618273e0",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        String inputJson = super.mapToJson(history);
        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.put(uri + "/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    @DisplayName("updateHistory should return updated history for given history")
    void updateHistory_ShouldReturnUpdatedHistory_ForGivenHistory() throws Exception {
        // GIVEN
        History history = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level update");

        String inputJson = super.mapToJson(history);
        // WHEN
        // THEN
        mvc.perform(MockMvcRequestBuilders.put(uri + "/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.note").value("Patient states that they are a short term Smoker Hemoglobin A1C above recommended level update"))
                .andReturn();
    }

    @Test
    @DisplayName("deleteHistory should delete history for given history id")
    void deleteHistory_ShouldReturn400StatusCode_ForGivenNotExistHistoryId() throws Exception {
        // GIVEN

        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete(uri + "/delete/618273e087de")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        // THEN
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("deleteHistory should delete history for given history id")
    void deleteHistory_ShouldDeleteHistory_ForGivenHistoryId() throws Exception {
        // GIVEN
        // WHEN
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.delete(uri + "/delete/618273e087def21060318688")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        // THEN
        assertEquals(204, mvcResult.getResponse().getStatus());
    }
}