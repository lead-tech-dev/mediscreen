package com.mediscreen.clientui.proxies;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.configuration.WireMockConfig;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
@Tag("PatientProxyTest")
@DisplayName("Patient proxy test logic")
class PatientProxyTest {
    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private PatientProxy patientProxy;

    @Test
    @DisplayName("addPatient, should return added patient")
    void whenAddPatient_thenAddedPatientShouldBeReturned() throws IOException {
        // GIVEN
        PatientMocks.setupMockAddPatientResponse(wireMockServer);
        PatientBean expected = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");

        // WHEN
        PatientBean response = patientProxy.addPatient(expected);

        // THEN
        assertEquals(response, expected);
    }

    @Test
    @DisplayName("getAllPatient, should return patient list")
    void whenGetAllPatient_thenPatientsShouldBeReturned() throws IOException {
        // GIVEN
        PatientMocks.setupMockGetAllPatientsResponse(wireMockServer);

        // WHEN
        Map<String, Object> responses = patientProxy.getAllPatient(1);

        // THEN
        assertFalse(responses.isEmpty());
        assertThat(responses, IsMapContaining.hasEntry("totalPages", 1));
        assertThat(responses, IsMapContaining.hasEntry("totalItems", 2));
        assertThat(responses, IsMapContaining.hasKey("patients"));

    }

    @Test
    @DisplayName("getPatient, should return  patient for given id")
    void whenGetPatient_thenPatientShouldBeReturned() throws IOException {
        // GIVEN
        PatientMocks.setupMockGetPatientResponse(wireMockServer);
        PatientBean expected = new PatientBean(
                1L,
                "TestNone",
                "Test",
                "1966-12-31 06:41:05",
                "F",
                "1 Brookside St",
                "100-222-3333");

        // WHEN
        PatientBean response = patientProxy.getPatient(1);

        // THEN
        assertEquals(response, expected);
    }

    @Test
    @DisplayName("updatePatient, should return updated patient for given patient")
    void whenUpdatePatient_thenUpdatedPatientShouldBeReturned() throws IOException {
        // GIVEN
        PatientMocks.setupMockUpdatePatientResponse(wireMockServer);
        PatientBean expected = new PatientBean(
                1L,
                "Ricky",
                "Maximan",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");

        // WHEN
        PatientBean response = patientProxy.updatePatient(expected);

        // THEN
        assertEquals(response, expected);
    }

    @Test
    @DisplayName("deletePatient, should delete given patient id")
    void whenDeletePatient_thenPatientShouldBeDelete() throws IOException {
        PatientMocks.setupMockDeletePatientResponse(wireMockServer);

        // WHEN
        PatientBean response = patientProxy.deletePatient(1);

        // THEN
        assertNull(response);
    }
}