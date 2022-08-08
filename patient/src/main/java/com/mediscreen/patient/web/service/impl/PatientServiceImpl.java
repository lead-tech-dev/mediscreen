package com.mediscreen.patient.web.service.impl;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.web.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * PatientServiceImpl. class that implement
 * patient business logic
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Patient> getAllPatient(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return patientRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Patient> getPatient(long id) {
        return patientRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePatient(long id) {
        patientRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
