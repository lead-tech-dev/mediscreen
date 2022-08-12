package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.bean.HistoryBean;
import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.web.controller.HistoryController;
import com.mediscreen.clientui.web.service.HistoryService;
import com.mediscreen.clientui.web.service.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("HistoryControllerTest")
@DisplayName("history controller test logic")
@SpringBootTest
@AutoConfigureMockMvc
class HistoryControllerTest extends AbstractTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @MockBean
    private HistoryService historyService;

    @MockBean
    private PatientService patientService;


    @Test
    @DisplayName("getHistoryPatientList should return patient history list")
    void getHistoryPatientList_shouldReturnPatientHistoryList() throws Exception {
        // GIVEN
        PatientBean patientBean = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        String uri = "/patient/" + historyBean.getPatientId() + "/history/list";
        when(patientService.getPatient(historyBean.getPatientId())).thenReturn(patientBean);
        when(historyService.getAllHistoryPatientById(historyBean.getPatientId())).thenReturn(List.of(historyBean));

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("history/list"))
                .andExpect(content().string(containsString("03-11-2021")))
                .andExpect(content().string(containsString(historyBean.getNote().substring(0, 24))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("showHistoryAddForm, should display history add form")
    void showHistoryAddForm_shouldRedirectToHistoryListForm_ForNotExistPatient() throws Exception {
        // GIVEN
        PatientBean patientBean = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        String uri = "/patient/" + patientBean.getId() + "/history/addForm";
        when(patientService.getPatient(patientBean.getId())).thenReturn(null);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("redirect:/patient/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("showHistoryAddForm, should display history add form")
    void showHistoryAddForm_shouldDisplayHistoryAddForm() throws Exception {
        // GIVEN
        PatientBean patientBean = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        String uri = "/patient/" + patientBean.getId() + "/history/addForm";
        when(patientService.getPatient(patientBean.getId())).thenReturn(patientBean);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("history/add"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("addHistory, should redirect to patient history list for given valid data")
    void addHistoryBlankInputField_ShouldDisplayValidationError_ForGivenBlankFieldData() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/history/add")
                        .param("patientId", "1")
                        .param("family", "Tella")
                        .param("note", ""))
                .andExpect(model().attributeHasFieldErrors(
                        "historyBean", "note"))
                .andExpect(content().string(containsString("must not be blank")))
                .andExpect(view().name("history/add"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("addHistory, should redirect to patient history list for given valid data")
    void addHistory_ShouldRedirectToPatientHistoryList_ForGivenValidData() throws Exception {
        //GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        // WHEN
        when(historyService.addHistory(any(HistoryBean.class))).thenReturn(historyBean);

        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/history/add")
                        .param("family", "Cartman")
                        .param("patientId", "1")
                        .param("note", "Patient states that they are a short term Smoker Hemoglobin A1C above recommended"))
                .andExpect(view().name("redirect:/patient/" + historyBean.getPatientId() + "/history/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("showHistoryUpdateForm, should display history update form")
    void showHistoryUpdateForm_shouldRedirectToHistoryListPage_ForNotExistGivenNotExistPatient() throws Exception {
        // GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        PatientBean patientBean = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        String uri = "/patient/" + patientBean.getId() + "/history/" + historyBean.getId() + "/updateForm";
        when(patientService.getPatient(patientBean.getId())).thenReturn(null);
        when(historyService.getHistory(historyBean.getId())).thenReturn(historyBean);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("redirect:/patient/" +patientBean.getId()+ "/history/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("showHistoryUpdateForm, should display history update form")
    void showHistoryUpdateForm_shouldRedirectToHistoryListPage_ForNotExistGivenNotExistHistory() throws Exception {
        // GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        PatientBean patientBean = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        String uri = "/patient/" + patientBean.getId() + "/history/" + historyBean.getId() + "/updateForm";
        when(patientService.getPatient(patientBean.getId())).thenReturn(patientBean);
        when(historyService.getHistory(historyBean.getId())).thenReturn(null);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("redirect:/patient/" +patientBean.getId()+ "/history/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("showHistoryUpdateForm, should display history update form")
    void showHistoryUpdateForm_shouldDisplayHistoryUpdateForm() throws Exception {
        // GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        PatientBean patientBean = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        String uri = "/patient/" + patientBean.getId() + "/history/" + historyBean.getId() + "/updateForm";
        when(patientService.getPatient(patientBean.getId())).thenReturn(patientBean);
        when(historyService.getHistory(historyBean.getId())).thenReturn(historyBean);

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("history/update"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("updatePatientBlankInputFieldError, should display error message for given blank input field")
    void updateHistoryBlankInputFieldError_ShouldDisplayValidationErrors_ForGivenBlankInputField() throws Exception {
        //GIVEN
        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/history/update")
                        .param("family", "cartman")
                        .param("note", ""))
                .andExpect(model().attributeHasFieldErrors(
                        "historyBean", "note"))
                .andExpect(content().string(containsString("must not be blank")))
                .andExpect(view().name("history/update"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("updateHistory, should redirect to patient history list for given valid data")
    void updateHistory_ShouldRedirectToPatientHistoryList_ForGivenValidData() throws Exception {
        //GIVEN
        HistoryBean expected = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        // WHEN
        when(historyService.updateHistory(any(HistoryBean.class))).thenReturn(expected);
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.post("/history/update")
                        .param("family", "cartman")
                        .param("note", "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level"))
                .andExpect(view().name("redirect:/patient/1/history/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("deletePatient, should redirect to patient list for given invalid id")
    void deleteHistory_ShouldRedirectToHistoryList_ForGivenInvalidPatientId() throws Exception {
        //GIVEN
        long patientId = 1000;
        String historyId = "618273e087def21060318688";

        // WHEN
        when(patientService.getPatient(anyLong())).thenReturn(null);
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get("/patient/" + patientId + "/history/delete/" + historyId))
                .andExpect(view().name("redirect:/patient/" + patientId + "/history/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("deletePatient, should redirect to patient list for given invalid id")
    void deleteHistory_ShouldRedirectToHistoryList_ForGivenInvalidHistoryId() throws Exception {
        //GIVEN
        PatientBean patientBean = new PatientBean(
                1L,
                "Ricky",
                "Maximan",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        String historyId = "618273e087def21060318688";

        // WHEN
        when(patientService.getPatient(anyLong())).thenReturn(patientBean);
        when(historyService.getHistory(anyString())).thenReturn(null);

        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get("/patient/" + patientBean.getId() + "/history/delete/" + historyId))
                .andExpect(view().name("redirect:/patient/" + patientBean.getId() + "/history/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("deleteHistory, should redirect to patient history list for given valid id")
    void deleteHistory_ShouldRedirectToPatientHistoryList_ForGivenValidId() throws Exception {
        //GIVEN
        PatientBean patientBean = new PatientBean(
                1L,
                "Ricky",
                "Maximan",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        // WHEN
        when(patientService.getPatient(anyLong())).thenReturn(patientBean);
        when(historyService.getHistory(anyString())).thenReturn(historyBean);

        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get("/patient/" + patientBean.getId() + "/history/delete/" + historyBean.getId()))
                .andExpect(view().name("redirect:/patient/" + patientBean.getId() + "/history/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }
}