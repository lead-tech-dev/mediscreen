package com.mediscreen.clientui.web.controller;

import com.mediscreen.clientui.bean.AssessmentBean;
import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.web.service.AssessmentService;
import com.mediscreen.clientui.web.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * AssessmentController. class that manage
 * request/response logic of user assessment.
 */
@Controller
public class AssessmentController {

    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Autowired
    private PatientService patientService;

    @Autowired
    private AssessmentService assessmentService;

    /**
     * getAssessment. Method that display patient assessment
     * result.
     *
     * @param model       a model
     * @param patientId a patient id
     * @return assessment view
     */
    @GetMapping("/assess/{patientId}")
    public String getAssessment(@PathVariable long patientId, Model model) {

        log.info("getting patient with id : {}", patientId);

        PatientBean patientBean = patientService.getPatient(patientId);

        if (patientBean == null) {
            log.error("patient doesn't exists with id : {}", patientId);
            return "redirect:/patient/list";
        }

        AssessmentBean assessment = assessmentService.getPatientAssessment(patientBean);

        model.addAttribute("assessment", assessment);

        log.info("successfully getting patient assessment: {}", assessment);

        return "assessment";
    }
}
