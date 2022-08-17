package com.mediscreen.patient.web.service;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * PatientService interface that structure the business logic
 * of patient.
 *
 */
public interface PatientService {

    /**
     * addPatient. Method that add given patient in database.
     *
     * @param patient a patient
     * @return a Patient
     */
    Patient addPatient(Patient patient);

    /**
     * getAllPatient. Method that get patient page from database.
     *
     * @param pageNumber a requested page
     * @return a map of String, Object
     */
    Page<Patient> getAllPatient(int pageNumber);

    /**
     * getPatient. Method that get one patient from database.
     *
     * @param id a patient id
     * @return Patient
     */
    Optional<Patient> getPatient(long id);

    /**
     * deletePatient. Method that delete given patient id in database.
     *
     * @param id a patient id
     */
    void deletePatient(long id);

    /**
     * updatePatient. Method that update given patient
     * in database.
     *
     * @param patient a patient
     * @return Patient
     */
    Patient updatePatient(Patient patient);

    /**
     * searchPatient. Method that search patient
     * in database.
     *
     * @param keyword a keyword
     * @return Patient list
     */
    List<Patient> searchPatient(String keyword);
}
