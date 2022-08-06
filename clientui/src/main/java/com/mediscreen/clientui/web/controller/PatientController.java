package com.mediscreen.clientui.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/patient/add")
    public String addPatient(Model model) {
        return "patient/add";
    }


    @GetMapping("/patient/update")
    public String updatePatient(Model model) {
        return "patient/update";
    }
}
