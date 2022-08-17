package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.bean.AssessmentBean;
import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.web.service.AssessmentService;
import com.mediscreen.clientui.web.service.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("AssessmentControllerTest")
@DisplayName("assessment controller test logic")
@SpringBootTest
@AutoConfigureMockMvc
class AssessmentControllerTest extends AbstractTest{

    @MockBean
    private AssessmentService assessmentService;

    @MockBean
    private PatientService patientService;

    @Test
    @DisplayName("getAssessment, should redirect to patient list for given not exists patient id")
    void getAssessment_ShouldRedirectToPatientList_ForGivenNotExistsPatientId() throws Exception {
        // GIVEN
        PatientBean patient = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        String uri = "/assess/" + patient.getId() ;
        when(patientService.getPatient(patient.getId())).thenReturn(null);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("redirect:/patient/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("getAssessment, should return patient assessment for given patient id")
    void getAssessment_ShouldReturnPatientAssessment_ForGivenPatientId() throws Exception {
// GIVEN
        PatientBean patient = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        AssessmentBean assessment = new AssessmentBean(
                "cartman",
                "eric",
                41,
                "Aucun risque (None)");

        String uri = "/assess/" + patient.getId() ;
        when(patientService.getPatient(patient.getId())).thenReturn(patient);
        when(assessmentService.getPatientAssessment(patient)).thenReturn(assessment);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("assessment"))
                .andExpect(content().string(containsString("cartman")))
                .andExpect(content().string(containsString("eric")))
                .andExpect(content().string(containsString("41")))
                .andExpect(content().string(containsString("Aucun risque (None)")))
                .andExpect(status().isOk())
                .andDo(print());
    }
}