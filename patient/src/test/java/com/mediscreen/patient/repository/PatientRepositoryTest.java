package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@Tag("PatientRepositoryTest")
@DisplayName("PatientRepository interface test")
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    @DisplayName("findPatientByKeyword, should return patient list for given search keyword")
    void findPatientByKeyword_ShouldReturnPatientList_ForGivenSearchKeyword() {
        // GIVEN
        Patient patient = new Patient();
        patient.setFamily("Cartman");
        patient.setGiven("Eric");
        patient.setDob(new Date());
        patient.setSex("M");
        patient.setPhone("999-444-9999");
        patient.setAddress("7 Rue Lucien Deneau");

        patientRepository.save(patient);

        // WHEN
        List<Patient> response = patientRepository.findPatientByKeyword("Cartman");

        // THEN
        assertThat(response).contains(patient);
    }
}