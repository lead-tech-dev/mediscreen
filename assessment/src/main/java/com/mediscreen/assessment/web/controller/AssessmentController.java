package com.mediscreen.assessment.web.controller;

import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.model.Patient;
import com.mediscreen.assessment.web.service.AssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssessmentController {


    private static final Logger log = LoggerFactory.getLogger(AssessmentController.class);
    @Autowired
    private AssessmentService assessmentService;

    /**
     * getAssessment. Method that get patient assessment.
     *
     * @param patient a given patient
     * @return Assessment object
     */
    @PostMapping("/assess")
    public ResponseEntity<Assessment> getAssessment(@RequestBody Patient patient) {

        log.info("Getting assessment with patient {}", patient);

        Assessment assessment = assessmentService.getPatientAssessment(patient);

        log.info("Getting assessment request success. Assessment data: {}",
                assessment);

        return new ResponseEntity<>(assessment, HttpStatus.OK);
    }
}
