package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PatientRepository. Interface that allows to access and
 * persist data between Patient object and patientdb database
 */
@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {
    @Query(value = "SELECT * FROM patient p WHERE p.family LIKE %:keyword%",
            nativeQuery =
                    true)
    List<Patient> findPatientByKeyword(@Param("keyword") String keyword);

}
