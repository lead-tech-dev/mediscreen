package com.mediscreen.patient.web.service.impl;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.utility.ConvertTo;
import com.mediscreen.patient.web.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Override
    public Patient addPatient(PatientDto patientDto) {
        Patient patient = ConvertTo.convertToPatient(patientDto);
        return patientRepository.save(patient);
    }
}
