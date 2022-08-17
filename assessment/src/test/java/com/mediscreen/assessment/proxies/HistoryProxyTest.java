package com.mediscreen.assessment.proxies;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.mediscreen.assessment.bean.HistoryBean;
import com.mediscreen.assessment.configuration.WireMockConfig;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
@Tag("HistoryProxyTest")
@DisplayName("History proxy test logic")
class HistoryProxyTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private HistoryProxy historyProxy;

    @Test
    @DisplayName("getAllHistoryByPatientId, should return patient history list")
    void whenGetAllHistoryByPatientId_thenPatientHistoryListShouldBeReturned() throws IOException, ParseException {
        // GIVEN
        Mocks.setupMockGetAllResponse(wireMockServer, "/history/getAll/1", "payload/get-history-response.json");
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

}