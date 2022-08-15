package com.mediscreen.patient.web.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.web.exception.BadRequestException;
import com.mediscreen.patient.web.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * PatientController. class that implement
 * request/response logic of Patient.
 */
@RestController
@RequestMapping("/patient")
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);
    @Autowired
    private PatientService patientService;

    /**
     * getAllPatient. Method that get current page list
     * of patient.
     *
     * @return map of String, Object
     */
    @GetMapping("/getAll/{pageNumber}")
    public Map<String, Object> getAllPatient(@PathVariable int pageNumber) {
        Map<String, Object> response = new HashMap<>();

        Page<Patient> page = patientService.getAllPatient(pageNumber);

        response.put("totalPages", page.getTotalPages());
        response.put("totalItems", page.getTotalElements());
        response.put("patients", page.getContent());

        log.info("Getting patient list : {}, of page {}", page.getContent(), pageNumber);

        return response;
    }

    /**
     * getPatient. Method that get one patient.
     *
     * @param id a given patient id
     * @return Patient object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") long id) {

        log.info("Getting patient with id {}", id);

        Optional<Patient> patient = patientService.getPatient(id);
        if (patient.isEmpty()) {

            log.error("Bad request exception: Patient not found!");

            throw new BadRequestException("Patient not found!");
        }

        log.info("Getting patient request success. Patient getting  data {}",
                patient.get());

        return new ResponseEntity<>(patient.get(), HttpStatus.OK);
    }

    /**
     * updatePatient. Method that update a patient.
     *
     * @param patient a given patient
     * @return Patient object
     */
    @PutMapping("/update")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {

        log.info("Updating patient with data {}", patient);

        Optional<Patient> exists = patientService.getPatient(patient.getId());
        if (exists.isEmpty()) {

            log.error("Bad request exception: Patient not found!");

            throw new BadRequestException("Patient not found!");
        }
        Patient updated = patientService.updatePatient(patient);

        log.info("Update patient request success. Patient updated with data {}",
                patient);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * addPatient. Method that save a patient.
     *
     * @param patient a given patient
     * @return patient object
     */
    @PostMapping("/add")
    public ResponseEntity<Patient> addPatient(@RequestBody @Valid Patient patient) {

        log.info("Creating patient with data: {}", patient);

        Patient added = patientService.addPatient(patient);

        log.info("Save patient request success. Patient saved with data {}",
                patient);

        return new ResponseEntity<>(added, HttpStatus.OK);
    }

    /**
     * deletePatient. Method that delete a patient.
     *
     * @param id a given patient id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") long id) {

        log.info("Deleting patient with id {}", id);

        Optional<Patient> exists = patientService.getPatient(id);

        if (exists.isEmpty()) {

            log.error("Patient not exists with id {}", id);

            throw new BadRequestException("Patient not exists!");
        }

        patientService.deletePatient(id);

        log.info("Delete patient request success with id {}", id);

        return ResponseEntity.noContent().build();
    }
}
