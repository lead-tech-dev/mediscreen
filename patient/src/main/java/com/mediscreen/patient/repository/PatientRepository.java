package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * PatientRepository. Interface that allows to access and
 * persist data between Patient object and patientdb database
 */
@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {
}
