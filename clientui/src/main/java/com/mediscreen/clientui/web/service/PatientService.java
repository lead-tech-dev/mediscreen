package com.mediscreen.clientui.web.service;

import com.mediscreen.clientui.bean.PatientBean;

import java.util.List;
import java.util.Map;

/**
 * PatientService interface that structure the business logic
 * of patient.
 *
 */
public interface PatientService {

    /**
     * getAllPatient. Method that get patient page info
     * from database.
     *
     * @param pageNumber a requested page
     * @return a map of String, Object
     */
    Map<String, Object> getAllPatient(int pageNumber);

    /**
     * addPatient. Method that add given patient in database.
     *
     * @param patientBean a patientBean
     * @return a PatientBean
     */
    PatientBean addPatient(PatientBean patientBean);

    /**
     * getPatient. Method that get one patient from database.
     *
     * @param id a patient id
     * @return PatientBean
     */
    PatientBean getPatient(long id);

    /**
     * updatePatient. Method that update given patientBean
     * in database.
     *
     * @param patientBean a patientBean
     * @return PatientBean
     */
    PatientBean updatePatient(PatientBean patientBean);

    /**
     * deletePatient. Method that delete given patient id in database.
     *
     * @param id a patient id
     */
    void deletePatient(long id);

    /**
     * searchPatient. Method that search patient in database.
     *
     * @param keyword a search keyword
     */
    List<PatientBean> searchPatient(String keyword);
}
