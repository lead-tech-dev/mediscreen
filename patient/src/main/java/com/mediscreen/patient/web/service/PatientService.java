package com.mediscreen.patient.web.service;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;

public interface PatientService {
    Patient addPatient(PatientDto patientDto);
}
