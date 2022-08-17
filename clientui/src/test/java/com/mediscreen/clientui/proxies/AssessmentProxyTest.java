package com.mediscreen.clientui.proxies;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.mediscreen.clientui.bean.AssessmentBean;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
@Tag("AssessmentProxyTest")
@DisplayName("Assessment proxy test logic")
class AssessmentProxyTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private AssessmentProxy assessmentProxy;

    @Test
    @DisplayName("getAssessment, should return patient assessment")
    void whenGetAssessment_thenAssessmentShouldBeReturned() throws IOException, ParseException {
        // GIVEN
        Mocks.setupMockAddResponse(wireMockServer, "/assess", "payload/assessment/get-assessment-response.json");
        AssessmentBean assessment = new AssessmentBean(
                "cartman",
                "eric",
                41,
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        PatientBean patient = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        // WHEN
        AssessmentBean response = assessmentProxy.getAssessment(patient);

        // THEN
        assertEquals(response.getFamily(), assessment.getFamily());
        assertEquals(response.getGiven(), assessment.getGiven());
        assertEquals(response.getBirthdate(), assessment.getBirthdate());
        assertEquals(response.getAssessment(), assessment.getAssessment());
    }
}