package com.mediscreen.clientui.web.controller;

import com.mediscreen.clientui.bean.HistoryBean;
import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.web.service.DateValidator;
import com.mediscreen.clientui.web.service.HistoryService;
import com.mediscreen.clientui.web.service.PatientService;
import com.mediscreen.clientui.web.service.impl.DateValidatorUsingDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * HistoryController. class that manage
 * request/response logic of user history.
 */
@Controller
public class HistoryController {

    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    private DateValidator dateValidator = new DateValidatorUsingDateFormat("yyyy-MM-dd");
    @Autowired
    private HistoryService historyService;

    @Autowired
    private PatientService patientService;



    /**
     * getHistoryPatientList. Method that display patient history
     * page.
     *
     * @param model       a model
     * @return history/list view
     */
    @GetMapping("/patient/{id}/history/list")
    public String getHistoryPatientList(Model model, @PathVariable("id") long id) {
        log.info("patient : {}", patientService.getPatient(id));
        PatientBean patientBean = patientService.getPatient(id);

        if (patientBean == null) {
            return "patient/list";
        }

        List<HistoryBean> histories = historyService.getAllHistoryPatientById(id);

        model.addAttribute("patient", patientBean);
        model.addAttribute("histories", histories);

        return "history/list";
    }


    /**
     * showHistoryAddForm. Method that display a history
     * add form.
     *
     * @return history/add view
     */
    @GetMapping("/patient/{id}/history/addForm")
    public String showHistoryAddForm(Model model, @PathVariable("id") long id) {

        PatientBean patientBean = patientService.getPatient(id);

        if (patientBean == null) {

            log.error("Patient not found!");

            return "redirect:/patient/list";
        }
        model.addAttribute("patientId", patientBean.getId());
        model.addAttribute("family", patientBean.getFamily());
        model.addAttribute("historyBean", new HistoryBean());

        log.info("Displaying history/add page!");

        return "history/add";
    }

    /**
     * addHistory. Method that handle a post request for
     * creating new history.
     *
     * @param model     a model
     * @param historyBean a historyBean
     * @param result    a BindingResult
     * @return history/list view
     */
    @PostMapping("/history/add")
    public String addHistory(@Valid HistoryBean historyBean, BindingResult result, Model model,
                             @ModelAttribute("family") String family, @ModelAttribute("patientId") long patientId) {


        if (result.hasErrors()) {
            model.addAttribute("patientId", patientId);
            model.addAttribute("family", family);
            log.error("{}, fields are blank", result.getModel());
            return "history/add";
        }

        historyBean.setDate(new Date());

        HistoryBean added = historyService.addHistory(historyBean);

        log.info("successfully history added, {}", added);

        return "redirect:/patient/" + added.getPatientId() + "/history/list";
    }


    /**
     * showHistoryUpdateForm. Method that display a history
     * update form.
     *
     * @param model a model
     * @param patientId patient id
     * @param historyId history id
     * @return history/update view
     */
    @GetMapping("/patient/{patientId}/history/{historyId}/updateForm")
    public String showHistoryUpdateForm(Model model, @PathVariable("historyId") String historyId, @PathVariable("patientId") long patientId) {
        PatientBean patientBean = patientService.getPatient(patientId);

        if (patientBean == null) {
            return "redirect:/patient/" + patientId + "/history/list";
        }

        HistoryBean historyBean = historyService.getHistory(historyId);

        if (historyBean == null) {
            return "redirect:/patient/" + patientId + "/history/list";
        }

        model.addAttribute("family", patientBean.getFamily());
        model.addAttribute("historyBean", historyBean);

        log.info("Displaying history/update page!");

        return "history/update";
    }

    /**
     * updateHistory. Method that handle a post request for
     * updating existing history.
     *
     * @param model     a model
     * @param historyBean a historyBean
     * @param result    a BindingResult
     * @return history/list view
     */
    @PostMapping("/history/update")
    public String updateHistory(@ModelAttribute("family") String family, Model model, @Valid HistoryBean historyBean, BindingResult result) {

        if (result.hasErrors()) {
            log.error("{}, fields are blank", result.getModel());

            model.addAttribute("family", family);

            return "history/update";
        }

        System.out.println(historyBean.getDate());

        HistoryBean history = historyService.updateHistory(historyBean);

        log.info("successfully updated history: {}", history);

        return "redirect:/patient/" + history.getPatientId() + "/history/list";
    }

    /**
     * deleteHistory. Method that handle a get request for
     * deleting existing history.
     *
     * @param model     a model
     * @param historyId a history id
     * @param patientId a patient id
     * @return history/list view
     */
    @GetMapping("/patient/{patientId}/history/delete/{historyId}")
    public String deleteHistory(Model model, @PathVariable("historyId") String historyId,
                                @PathVariable("patientId") long patientId) {

        PatientBean patientBean = patientService.getPatient(patientId);

        if (patientBean == null) {
            return "redirect:/patient/" + patientId + "/history/list";
        }

        HistoryBean historyBean = historyService.getHistory(historyId);

        if (historyBean == null) {
            return "redirect:/patient/" + patientId + "/history/list";
        }

        historyService.deleteHistory(historyId);

        log.info("successfully deleted for id: {}", historyId);

        return "redirect:/patient/" + patientBean.getId() + "/history/list";
    }

}
