package com.mediscreen.assessment.web.service;

import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.model.Patient;

/**
 * AssessmentService interface that structure the business logic
 * of assessment.
 *
 */
public interface AssessmentService {

    /**
     * getPatientAssessment. Method that determine
     * patient assessment.
     *
     * @param patient a patient
     * @return an Assessment object
     */
    Assessment getPatientAssessment(Patient patient);
}
