package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.web.service.impl.PatientServiceImpl;
import org.assertj.core.api.Assertions;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Tag("PatientServiceImplTest")
@DisplayName("patient service implementation class")
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;


    @Test
    @DisplayName("getAllPatient should return patient page list for given page number")
    void getAllPatient_ShouldReturnPatientList() {
        // GIVEN
        int pageNumber = 1;
        Patient expected = new Patient(
                1L,
                "Cartman",
                "Eric",
                new Date(),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        Page<Patient> patients = new PageImpl(List.of(expected));
        when(patientRepository.findAll(pageable)).thenReturn(patients);

        // WHEN
        Page<Patient> results = patientService.getAllPatient(pageNumber);

        // THEN
        verify(patientRepository, times(1)).findAll(pageable);
        Assertions.assertThat(results).contains(expected);
    }

    @Test
    @DisplayName("searchPatient should return patient list for given keyword")
    void searchPatient_ShouldReturnPatientList_ForGivenKeyword() {
        // GIVEN
        Patient expected = new Patient(
                1L,
                "Cartman",
                "Eric",
                new Date(),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        when(patientRepository.findPatientByKeyword("Cartman")).thenReturn(List.of(expected));

        // WHEN
        List<Patient> results = patientService.searchPatient("Cartman");

        // THEN
        verify(patientRepository, times(1)).findPatientByKeyword("Cartman");
        Assertions.assertThat(results).contains(expected);
    }

    @Test
    @DisplayName("addPatient should return added patient for given patient")
    void addPatient_ShouldReturnAddedPatient_ForGivenPatient() {
        // GIVEN
        Patient expected = new Patient(
                1L,
                "Cartman",
                "Eric",
                new Date(),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        when(patientRepository.save(expected)).thenReturn(expected);

        // WHEN
        Patient response = patientService.addPatient(expected);

        // THEN
        assertEquals(response, expected);
    }

    @Test
    @DisplayName("getPatient should return patient for given patient id")
    void getPatient_ShouldReturnPatient_ForGivenPatientId() {
        // GIVEN
        Patient expected = new Patient(
                1L,
                "TestNone",
                "Test",
                new Date(),
                "F",
                "1 Brookside St",
                "100-222-3333");
        when(patientRepository.findById(1L)).thenReturn(Optional.of(expected));

        // WHEN
        Optional<Patient> response = patientService.getPatient(1);

        // THEN
        assertEquals(response.get(), expected);
    }

    @Test
    @DisplayName("updatePatient should return updated patient for given patient")
    void updatePatient_ShouldReturnUpdatedPatient_ForGivenPatient() throws ParseException {
        // GIVEN
        Patient expected = new Patient(
                1L,
                "TestNone",
                "Test",
                new SimpleDateFormat("yyyy-MM-dd").parse("1966-12-31 06:41:05"),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        when(patientRepository.save(expected)).thenReturn(expected);

        // WHEN
        Patient response = patientService.updatePatient(expected);

        // THEN
        assertEquals(response, expected);
    }

    @Test
    @DisplayName("deletePatient should delete patient for given patient id")
    void deletePatient_ShouldDeletePatient_ForGivenPatientId() {
        doNothing().when(patientRepository).deleteById(4L);

        // WHEN
        patientService.deletePatient(4);

        // THEN
        Optional<Patient> patient = patientService.getPatient(4L);
        assertTrue(patient.isEmpty());
    }
}