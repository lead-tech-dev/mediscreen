package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * PatientProxy. Interface that manage patient
 * microservice endPoints.
 *
 */
@FeignClient(name = "patient", url = "localhost:8081")
public interface PatientProxy {

    /**
     * addPatient. Method that add given patient in database.
     *
     * @param patientBean a patientBean
     * @return an PatientBean
     */
    @PostMapping( value = "/patient/add")
    PatientBean addPatient(@RequestBody PatientBean patientBean);

    /**
     * getAllPatient. Method that get patient page from database.
     *
     * @param pageNumber a requested page
     * @return a map of String, Object
     */
    @GetMapping( value = "/patient/getAll/{pageNumber}")
    Map<String, Object> getAllPatient(@PathVariable("pageNumber") int pageNumber);

    /**
     * getPatient. Method that get one patient from database.
     *
     * @param id a patient id
     * @return PatientBean
     */
    @GetMapping( value = "/patient/get/{id}")
    PatientBean getPatient(@PathVariable("id") long id);

    /**
     * updatePatient. Method that update given patient
     * in database.
     *
     * @param patientBean a patient
     * @return PatientBean
     */
    @PutMapping( value = "/patient/update")
    PatientBean updatePatient(@RequestBody  PatientBean patientBean);

    /**
     * deletePatient. Method that delete given patient id in database.
     *
     * @param id a patient id
     */
    @DeleteMapping( value = "/patient/delete/{id}")
    PatientBean deletePatient(@PathVariable("id") long id);
}
