package com.mediscreen.clientui.service;

import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.proxies.PatientMocks;
import com.mediscreen.clientui.proxies.PatientProxy;
import com.mediscreen.clientui.web.service.PatientService;
import com.mediscreen.clientui.web.service.impl.PatientServiceImpl;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Tag("PatientServiceImplTest")
@DisplayName("patient service implementation class")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class PatientServiceImplTest {

    @Mock
    private PatientProxy patientProxy;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    @DisplayName("getAllPatient should return patient list")
    void getAllPatient_ShouldReturnPatientList() {
        // GIVEN
        Map<String, Object> data = new HashMap<>();
        data.put("totalPages", 1);
        data.put("totalItems", 1);
        data.put("patients", List.of(new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999")));
        when(patientProxy.getAllPatient(1)).thenReturn(data);

        // WHEN
        Map<String, Object> responses = patientService.getAllPatient(1);

        // THEN
        assertFalse(responses.isEmpty());
        assertThat(responses, IsMapContaining.hasEntry("totalPages", 1));
        assertThat(responses, IsMapContaining.hasEntry("totalItems", 1));
        assertThat(responses, IsMapContaining.hasKey("patients"));
    }

    @Test
    @DisplayName("addPatient should return added patient for given patient")
    void addPatient_ShouldReturnAddedPatient_ForGivenPatient() {
        // GIVEN
        PatientBean expected = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        when(patientProxy.addPatient(expected)).thenReturn(expected);

        // WHEN
        PatientBean response = patientService.addPatient(expected);

        // THEN
        assertEquals(response, expected);
    }

    @Test
    @DisplayName("getPatient should return patient for given patient id")
    void getPatient_ShouldReturnPatient_ForGivenPatientId() {
        // GIVEN
        PatientBean expected = new PatientBean(
                1L,
                "TestNone",
                "Test",
                "1966-12-31 06:41:05",
                "F",
                "1 Brookside St",
                "100-222-3333");
        when(patientProxy.getPatient(1)).thenReturn(expected);

        // WHEN
        PatientBean response = patientService.getPatient(1);

        // THEN
        assertEquals(response, expected);
    }

    @Test
    @DisplayName("updatePatient should return updated patient for given patient")
    void updatePatient_ShouldReturnUpdatedPatient_ForGivenPatient() {
        // GIVEN
        PatientBean expected = new PatientBean(
                1L,
                "Ricky",
                "Maximan",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        when(patientProxy.updatePatient(expected)).thenReturn(expected);

        // WHEN
        PatientBean response = patientService.updatePatient(expected);

        // THEN
        assertEquals(response, expected);
    }

    @Test
    @DisplayName("deletePatient should delete patient for given patient id")
    void deletePatient_ShouldDeletePatient_ForGivenPatientId() {
        when(patientProxy.deletePatient(1)).thenReturn(null);

        // WHEN
        PatientBean response = patientProxy.deletePatient(1);

        // THEN
        assertNull(response);
    }
}