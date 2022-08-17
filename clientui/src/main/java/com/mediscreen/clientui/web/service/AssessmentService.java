package com.mediscreen.clientui.web.service;


import com.mediscreen.clientui.bean.AssessmentBean;
import com.mediscreen.clientui.bean.PatientBean;

/**
 * AssessmentService interface that structure the business logic
 * of assessment.
 *
 */
public interface AssessmentService {
    /**
     * getPatientAssessment. Method that get patient
     * assessment.
     *
     * @param patientBean an patientBean
     * @return an AssessmentBean
     */
    AssessmentBean getPatientAssessment(PatientBean patientBean);
}
