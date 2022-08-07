package com.mediscreen.clientui.web.controller;

import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.proxies.PatientProxy;
import com.mediscreen.clientui.web.service.DateValidator;
import com.mediscreen.clientui.web.service.impl.DateValidatorUsingDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PatientController {

    private DateValidator dateValidator = new DateValidatorUsingDateFormat("MM-dd-yyyy");
    @Autowired
    private PatientProxy patientProxy;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/patient/addForm")
    public String showPatientForm(Model model) {
        model.addAttribute("patientBean", new PatientBean());
        return "patient/add";
    }

    @PostMapping("/patient/add")
    public String addPatient(@Valid PatientBean patientBean, BindingResult result, Model model) {
        System.out.println(patientBean.toString());
        if (result.hasErrors()) {
            return "patient/add";
        }

        if (!dateValidator.isValid(patientBean.getBirthdate())) {
            System.out.println(patientBean.getBirthdate());
            result.rejectValue("birthdate", "error.badDateFormat",
                    "Format accepté : MM-dd-yyyy!");
            return "patient/add";
        }

        patientProxy.addPatient(patientBean);
        return "redirect:patient/list";
    }


    @GetMapping("/patient/updateForm")
    public String updatePatient(Model model) {
        return "patient/update";
    }


}
