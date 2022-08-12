package com.mediscreen.clientui.proxies;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.mediscreen.clientui.bean.HistoryBean;
import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.configuration.WireMockConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
@Tag("HistoryProxyTest")
@DisplayName("History proxy test logic")
class HistoryProxyTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private HistoryProxy historyProxy;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    @DisplayName("addHistory, should return added history")
    void whenAddHistory_thenAddedHistoryShouldBeReturned() throws IOException, ParseException {
        // GIVEN
        Mocks.setupMockAddResponse(wireMockServer, "/history/add", "payload/history/add-history-response.json");
        HistoryBean expected = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        // WHEN

        HistoryBean response = historyProxy.addHistory(expected);

        // THEN
        assertEquals(response.getPatientId(), expected.getPatientId());
        assertEquals(response.getId(), expected.getId());
        assertEquals(response.getNote(), expected.getNote());
        assertEquals(simpleDateFormat.format(response.getDate()), simpleDateFormat.format(expected.getDate()));
    }

    @Test
    @DisplayName("getAllHistoryByPatientId, should return patient history list")
    void whenGetAllHistoryByPatientId_thenPatientHistoryListShouldBeReturned() throws IOException, ParseException {
        // GIVEN
        Mocks.setupMockGetAllResponse(wireMockServer, "/history/getAll/1", "payload/history/get-histories-response.json");
        HistoryBean expected = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        // WHEN
        List<HistoryBean> responses = historyProxy.getAllHistoryByPatientId(1);

        // THEN
        assertFalse(responses.isEmpty());
        assertEquals(responses.get(0).getPatientId(), expected.getPatientId());
        assertEquals(responses.get(0).getId(), expected.getId());
        assertEquals(responses.get(0).getNote(), expected.getNote());
        assertEquals(simpleDateFormat.format(responses.get(0).getDate()), simpleDateFormat.format(expected.getDate()));


    }

    @Test
    @DisplayName("getHistory, should return  history for given id")
    void whenGetHistory_thenHistoryShouldBeReturned() throws IOException, ParseException {
        // GIVEN
        HistoryBean expected = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        Mocks.setupMockGetResponse(wireMockServer, "/history/get/" + expected.getId(), "payload/history/get-history-response.json");


        // WHEN
        HistoryBean response = historyProxy.getHistory(expected.getId());

        // THEN
        assertEquals(response.getPatientId(), expected.getPatientId());
        assertEquals(response.getId(), expected.getId());
        assertEquals(response.getNote(), expected.getNote());
        assertEquals(simpleDateFormat.format(response.getDate()), simpleDateFormat.format(expected.getDate()));

    }

    @Test
    @DisplayName("updateHistory, should return updated history for given history")
    void whenUpdateHistory_thenUpdatedHistoryShouldBeReturned() throws IOException, ParseException {
        // GIVEN
        HistoryBean expected = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        Mocks.setupMockUpdateResponse(wireMockServer, "/history/update", "payload/history/update-history-response.json");

        // WHEN
        HistoryBean response = historyProxy.updateHistory(expected);

        // THEN
        assertEquals(response.getPatientId(), expected.getPatientId());
        assertEquals(response.getId(), expected.getId());
        assertEquals(response.getNote(), expected.getNote());
        assertEquals(simpleDateFormat.format(response.getDate()), simpleDateFormat.format(expected.getDate()));

    }

    @Test
    @DisplayName("deleteHistory, should delete given history id")
    void whenDeleteHistory_thenHistoryShouldBeDelete() throws IOException {
        Mocks.setupMockDeleteResponse(wireMockServer, "/history/delete/618273e087def21060318688");

        // WHEN
        HistoryBean response = historyProxy.deleteHistory("618273e087def21060318688");

        // THEN
        assertNull(response);
    }
}