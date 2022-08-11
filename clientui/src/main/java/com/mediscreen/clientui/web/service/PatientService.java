package com.mediscreen.clientui.web.service;

import com.mediscreen.clientui.bean.PatientBean;

import java.util.Map;

public interface PatientService {
    Map<String, Object> getAllPatient(int pageNumber);

    PatientBean addPatient(PatientBean patientBean);

    PatientBean getPatient(long id);

    PatientBean updatePatient(PatientBean patientBean);

    void deletePatient(long id);
}
