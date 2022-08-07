package com.mediscreen.patient.web.controller;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.utility.ConvertTo;
import com.mediscreen.patient.web.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<PatientDto> addPatient(@RequestBody @Valid PatientDto patientDto) {
        Patient added = patientService.addPatient(patientDto);
        return new ResponseEntity<>(ConvertTo.convertToPatientDto(added), HttpStatus.OK);
    }
}
