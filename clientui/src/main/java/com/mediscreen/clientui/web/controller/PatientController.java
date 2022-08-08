package com.mediscreen.clientui.web.controller;

import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.proxies.PatientProxy;
import com.mediscreen.clientui.web.service.DateValidator;
import com.mediscreen.clientui.web.service.impl.DateValidatorUsingDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);
    private DateValidator dateValidator = new DateValidatorUsingDateFormat("yyyy-MM-dd");
    @Autowired
    private PatientProxy patientProxy;

    /**
     * home. Method that display a home page.
     *
     * @return home view
     */

    @GetMapping("/")
    public String home() {
        log.info("Displaying home page!");
        return "home";
    }

    /**
     * getPatientList. Method that display first patient page.
     *
     * @param model       a model
     * @return patient/list view
     */
    @GetMapping("/patient/list")
    public String getPatientList(Model model) {
        return getOnePatient(model, 1);
    }

    /**
     * getOnePatient. Method that display current patient page number.
     *
     * @param model       a model
     * @param currentPage a current page number
     * @return patient/list view
     */
    @GetMapping("/patient/list/{currentPage}")
    public String getOnePatient(Model model, @PathVariable("currentPage") int currentPage) {

        Map<String, Object> response = patientProxy.getAllPatient(currentPage);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("patients", response.get("patients"));
        model.addAttribute("totalPages", response.get("totalPages"));
        model.addAttribute("totalItems", response.get("totalItems"));

        log.info("Displaying patient/list page!");

        return "patient/list";
    }

    /**
     * showPatientForm. Method that display an add form.
     *
     * @return patient/add view
     */
    @GetMapping("/patient/addForm")
    public String showPatientForm(Model model) {
        model.addAttribute("patientBean", new PatientBean());

        log.info("Displaying patient/add page!");

        return "patient/add";
    }

    /**
     * addPatient. Method that handle a post request for
     * creating new patient.
     *
     * @param model     a model
     * @param patientBean a patientBean
     * @param result    a BindingResult
     * @return patient/list view
     */
    @PostMapping("/patient/add")
    public String addPatient(@Valid PatientBean patientBean, BindingResult result, Model model) {
        System.out.println(patientBean.toString());
        if (result.hasErrors()) {
            log.error("{}, fields are blank", result.getErrorCount());
            return "patient/add";
        }

        if (!dateValidator.isValid(patientBean.getDob())) {
            log.error("{}, error date format", patientBean.getDob());
            result.rejectValue("dob", "error.badDateFormat",
                    "Format accepté : yyyy-MM-dd!");
            return "patient/add";
        }

        patientProxy.addPatient(patientBean);

        log.info("successfully patient added, {}", patientBean);

        return "redirect:patient/list";
    }


    /**
     * showUpdateForm. Method that display an update form.
     *
     * @param model a model
     * @param id patient id
     * @return patient/update view
     */
    @GetMapping("/patient/updateForm/{id}")
    public String showUpdateForm(Model model, @PathVariable long id) {
        PatientBean patient = patientProxy.getPatient(id);
        model.addAttribute("patientBean", patient);

        log.info("Displaying patient/update page!");

        return "patient/update";
    }

    /**
     * updatePatient. Method that handle a post request for
     * updating existing patient.
     *
     * @param model     a model
     * @param patientBean a patientBean
     * @param result    a BindingResult
     * @return patient/list view
     */
    @PostMapping("/patient/update")
    public String updatePatient(Model model, @Valid PatientBean patientBean, BindingResult result) {

        if (result.hasErrors()) {
            log.error("{}, fields are blank", result.getErrorCount());
            return "patient/update";
        }

        if (!dateValidator.isValid(patientBean.getDob())) {

            log.error("{}, error date format", patientBean.getDob());

            result.rejectValue("dob", "error.badDateFormat",
                    "Format accepté : MM-dd-yyyy!");

            return "patient/update";
        }

        PatientBean patient = patientProxy.updatePatient(patientBean);
        return "redirect:/patient/list";
    }

    /**
     * deletePatient. Method that handle a get request for
     * deleting existing patient.
     *
     * @param model     a model
     * @param id a patient id
     * @return patient/list view
     */
    @GetMapping("/patient/delete/{id}")
    public String deletePatient(Model model, @PathVariable long id) {

        patientProxy.deletePatient(id);

        log.error("{}, successfully deleted for id ", id);

        return "redirect:/patient/list";
    }

}
