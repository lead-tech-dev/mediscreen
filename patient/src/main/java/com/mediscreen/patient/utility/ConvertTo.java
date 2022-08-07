package com.mediscreen.patient.utility;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;

public class ConvertTo {

    public static Patient convertToPatient(PatientDto patientDto) {
        return new Patient(
                patientDto.getId(),
                patientDto.getFamily(),
                patientDto.getGiven(),
                patientDto.getDob(),
                patientDto.getSex(),
                patientDto.getAddress(),
                patientDto.getPhone());
    }

    public static PatientDto convertToPatientDto(Patient patient) {
        return new PatientDto(
                patient.getId(),
                patient.getFamily(),
                patient.getGiven(),
                patient.getDob(),
                patient.getSex(),
                patient.getAddress(),
                patient.getPhone()
        );
    }
}
