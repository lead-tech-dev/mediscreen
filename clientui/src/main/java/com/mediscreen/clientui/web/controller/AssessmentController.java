package com.mediscreen.clientui.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AssessmentController {

    @GetMapping("/assess/{patientId}")
    public String getAssessment(@PathVariable String patientId) {
        return "assessment";
    }
}
