package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.proxies.PatientProxy;
import com.mediscreen.clientui.web.exception.PatientBadRequestException;
import com.mediscreen.clientui.web.service.PatientService;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("PatientControllerTest")
@DisplayName("Patient controller test logic")
class PatientControllerTest extends AbstractTest{

    @MockBean
    private PatientService patientService;


    @Test
    @DisplayName("searchPatient should return empty list for given not exists keyword")
    void searchPatient_shouldReturnEmptyList_ForGivenNotExistKeyword() throws Exception {
        // GIVEN
        String uri = "/patient/search?keyword=keyword";

        when(patientService.searchPatient("keyword")).thenReturn(Collections.emptyList());

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("result"))
                .andExpect(content().string(containsString("No result available")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("searchPatient should return patient list for given keyword")
    void searchPatient_shouldReturnPatientList_ForGivenKeyword() throws Exception {
        // GIVEN
        String uri = "/patient/search?keyword=Cartman";
        PatientBean patient = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        when(patientService.searchPatient("Cartman")).thenReturn(List.of(patient));

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("result"))
                .andExpect(content().string(containsString("Cartman")))
                .andExpect(content().string(containsString("Eric")))
                .andExpect(content().string(containsString("1981-02-23 06:41:05")))
                .andExpect(content().string(containsString("999-444-9999")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("getPatientList should return patient list")
    void getPatientList_shouldReturnFirstPagePatientList() throws Exception {
        // GIVEN
        String uri = "/patient/list";
        Map<String, Object> data = new HashMap<>();
        data.put("totalPages", 1);
        data.put("totalItems", 1);
        data.put("patients", List.of(new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999")));
        when(patientService.getAllPatient(1)).thenReturn(data);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("patient/list"))
                .andExpect(content().string(containsString("Cartman")))
                .andExpect(content().string(containsString("Eric")))
                .andExpect(content().string(containsString("1981-02-23 06:41:05")))
                .andExpect(content().string(containsString("999-444-9999")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("getPatientList should return patient list")
    void getOnePatient_shouldReturnGivenNumberPagePatientList() throws Exception {
        // GIVEN
        String uri = "/patient/list/2";
        Map<String, Object> data = new HashMap<>();
        data.put("totalPages", 1);
        data.put("totalItems", 1);
        data.put("patients", List.of(new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999")));
        when(patientService.getAllPatient(2)).thenReturn(data);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("patient/list"))
                .andExpect(model().attribute("currentPage", 2))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("totalItems", 1))
                .andExpect(model().attribute("patients", data.get("patients")))
                .andExpect(content().string(containsString("Cartman")))
                .andExpect(content().string(containsString("Eric")))
                .andExpect(content().string(containsString("1981-02-23 06:41:05")))
                .andExpect(content().string(containsString("999-444-9999")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("patientAddForm, should display patient add form")
    void patientAddForm_shouldDisplayPatientAddForm() throws Exception {
        // GIVEN
        String uri = "/patient/addForm";

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("patient/add"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("addPatientBlankInputFieldError, should display error message for given blank input field")
    void addPatientBlankInputFieldError_ShouldDisplayValidationErrors_ForGivenBlankInputField() throws Exception {
        //GIVEN
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/patient/add")
                        .param("family", "")
                        .param("given", "")
                        .param("dob", ""))
                .andExpect(model().attributeHasFieldErrors(
                        "patientBean", "family", "given", "dob"))
                .andExpect(content().string(containsString("must not be blank")))
                .andExpect(view().name("patient/add"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("addPatientInvalidDobFormat, should return error message for given invalid date format")
    void addPatientInvalidDobFormat_ShouldReturnValidationErrors_ForGivenInvalidDobFormat() throws Exception {
        //GIVEN
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/patient/add")
                        .param("family", "Cartman")
                        .param("given", "Eric")
                        .param("dob", "1111111")
                        .param("sex", "F"))
                .andExpect(model().attributeHasFieldErrors(
                        "patientBean", "dob"))
                .andExpect(content().string(containsString("Format accepté : yyyy-MM-dd!")))
                .andExpect(view().name("patient/add"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("addPatient, should redirect to patient list for given valid data")
    void addPatient_ShouldRedirectToPatientList_ForGivenValidData() throws Exception {
        //GIVEN
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/patient/add")
                        .param("family", "Cartman")
                        .param("given", "Eric")
                        .param("dob", "2012-02-23")
                        .param("sex", "M"))
               .andExpect(view().name("redirect:patient/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }


    @Test
    @DisplayName("showUpdateForm, should display patient update form")
    void showUpdateForm_shouldDisplayPatientUpdateForm() throws Exception {
        // GIVEN
        String uri = "/patient/updateForm/1";
        when(patientService.getPatient(1)).thenReturn(new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999"));
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("patient/update"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("updatePatientBlankInputFieldError, should display error message for given blank input field")
    void updatePatientBlankInputFieldError_ShouldDisplayValidationErrors_ForGivenBlankInputField() throws Exception {
        //GIVEN
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .param("family", "")
                        .param("given", "")
                        .param("dob", ""))
                .andExpect(model().attributeHasFieldErrors(
                        "patientBean", "family", "given", "dob"))
                .andExpect(content().string(containsString("must not be blank")))
                .andExpect(view().name("patient/update"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("updatePatientInvalidDobFormat, should return error message for given invalid date format")
    void updatePatientInvalidDobFormat_ShouldReturnValidationErrors_ForGivenInvalidDobFormat() throws Exception {
        //GIVEN
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .param("family", "Cartman")
                        .param("given", "Eric")
                        .param("dob", "1111111")
                        .param("sex", "F"))
                .andExpect(model().attributeHasFieldErrors(
                        "patientBean", "dob"))
                .andExpect(content().string(containsString("Format accepté : yyyy-MM-dd!")))
                .andExpect(view().name("patient/update"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("updatePatient, should redirect to patient list for given valid data")
    void updatePatient_ShouldRedirectToPatientList_ForGivenValidData() throws Exception {
        //GIVEN
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .param("family", "Maximilien")
                        .param("given", "Eric")
                        .param("dob", "2012-02-23")
                        .param("sex", "M"))
                .andExpect(view().name("redirect:patient/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("deletePatient, should redirect to patient list for given invalid id")
    void deletePatient_ShouldRedirectToPatientList_ForGivenInvalidId() throws Exception {
        //GIVEN
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get("/patient/delete/3"))
                .andExpect(view().name("redirect:patient/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("deletePatient, should redirect to patient list for given valid id")
    void deletePatient_ShouldRedirectToPatientList_ForGivenValidId() throws Exception {
        //GIVEN
        // WHEN
        when(patientService.getPatient(1)).thenReturn(new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999"));
        doNothing().when(patientService).deletePatient(1);

        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get("/patient/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }
}