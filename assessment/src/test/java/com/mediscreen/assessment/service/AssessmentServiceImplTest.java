package com.mediscreen.assessment.service;

import com.mediscreen.assessment.bean.HistoryBean;
import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.model.Patient;
import com.mediscreen.assessment.proxies.HistoryProxy;
import com.mediscreen.assessment.web.service.impl.AssessmentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Tag("AssessmentServiceImplTest")
@DisplayName("assessment service implementation class")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AssessmentServiceImplTest {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Mock
    private HistoryProxy historyProxy;

    @InjectMocks
    private AssessmentServiceImpl assessmentService;


    @Test
    @DisplayName("getPatientAssessment should return patient assessment None")
    void getPatientAssessment_ShouldReturnAssessmentNone_ForGivenPatientNoTrigger() throws ParseException {
        // GIVEN
        Patient patient = new Patient(
                1L,
                "Cartman",
                "Eric",
                simpleDateFormat.parse("1998-08-02"),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean history1 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare que tout semble aller bien");

        when(historyProxy.getAllHistoryByPatientId(history1.getPatientId())).thenReturn(List.of(history1));

        // WHEN
        Assessment response = assessmentService.getPatientAssessment(patient);

        // THEN
        assertThat(response.getAssessment()).isEqualTo("Aucun risque (None)");
    }

    @Test
    @DisplayName("getPatientAssessment should return patient assessment Borderline")
    void getPatientAssessment_ShouldReturnBorderline_ForGivenPatientTwoTriggerAgeGreaterThan30() throws ParseException {
        // GIVEN
        Patient patient = new Patient(
                1L,
                "Cartman",
                "Eric",
                simpleDateFormat.parse("1988-08-02"),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean history1 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        HistoryBean history2 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.");

        when(historyProxy.getAllHistoryByPatientId(history1.getPatientId())).thenReturn(List.of(history1, history2));

        // WHEN
        Assessment response = assessmentService.getPatientAssessment(patient);

        // THEN
        assertThat(response.getAssessment()).isEqualTo("Risque limité (Borderline)");
    }

    @Test
    @DisplayName("getPatientAssessment should return patient assessment In Danger")
    void getPatientAssessment_ShouldReturnInDanger_ForGivenPatientWomenFourTrigger()
            throws ParseException {
        // GIVEN
        Patient patient = new Patient(
                1L,
                "Cartmanine",
                "Ericka",
                simpleDateFormat.parse("1988-08-02"),
                "F",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean history1 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        HistoryBean history2 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.");

        HistoryBean history3 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé");

        HistoryBean history4 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare qu'il est fumeur occasionnel");

        when(historyProxy.getAllHistoryByPatientId(history1.getPatientId())).thenReturn(List.of(history1, history2, history3, history4));

        // WHEN
        Assessment response = assessmentService.getPatientAssessment(patient);

        // THEN
        assertThat(response.getAssessment()).isEqualTo("Danger (In Danger)");
    }

    @Test
    @DisplayName("getPatientAssessment should return patient assessment In Danger")
    void getPatientAssessment_ShouldReturnInDanger_ForGivenPatientMenTreeTrigger()
            throws ParseException {
        // GIVEN
        Patient patient = new Patient(
                1L,
                "Cartman",
                "Erick",
                simpleDateFormat.parse("1988-08-02"),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean history1 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        HistoryBean history2 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.");

        HistoryBean history3 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé");


        when(historyProxy.getAllHistoryByPatientId(history1.getPatientId())).thenReturn(List.of(history1, history2, history3));

        // WHEN
        Assessment response = assessmentService.getPatientAssessment(patient);

        // THEN
        assertThat(response.getAssessment()).isEqualTo("Danger (In Danger)");
    }

    @Test
    @DisplayName("getPatientAssessment should return patient assessment In Danger")
    void getPatientAssessment_ShouldReturnInDanger_ForGivenPatientSixTriggerAgeGreaterThan30()
            throws ParseException {
        // GIVEN
        Patient patient = new Patient(
                1L,
                "Cartman",
                "Erick",
                simpleDateFormat.parse("1988-08-02"),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean history1 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        HistoryBean history2 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.");

        HistoryBean history3 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé");

        HistoryBean history4 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare qu'il est fumeur occasionnel");


        HistoryBean history5 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant une microalbumine élevée");

        HistoryBean history6 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant une perte de poids depuis une semaine");


        when(historyProxy.getAllHistoryByPatientId(history1.getPatientId())).thenReturn(
                List.of(history1, history2, history3, history4, history5, history6));

        // WHEN
        Assessment response = assessmentService.getPatientAssessment(patient);

        // THEN
        assertThat(response.getAssessment()).isEqualTo("Danger (In Danger)");
    }


    @Test
    @DisplayName("getPatientAssessment should return patient assessment Early Onset")
    void getPatientAssessment_ShouldReturnEarlyOnset_ForGivenPatientMenFiveTriggerAgeLessThan30()
            throws ParseException {
        // GIVEN
        Patient patient = new Patient(
                1L,
                "Cartman",
                "Erick",
                simpleDateFormat.parse("1998-08-02"),
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean history1 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        HistoryBean history2 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.");

        HistoryBean history3 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé");

        HistoryBean history4 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare qu'il est fumeur occasionnel");


        HistoryBean history5 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant une microalbumine élevée");



        when(historyProxy.getAllHistoryByPatientId(history1.getPatientId())).thenReturn(
                List.of(history1, history2, history3, history4, history5));

        // WHEN
        Assessment response = assessmentService.getPatientAssessment(patient);

        // THEN
        assertThat(response.getAssessment()).isEqualTo("Apparition précoce (Early onset)");
    }

    @Test
    @DisplayName("getPatientAssessment should return patient assessment Early Onset")
    void getPatientAssessment_ShouldReturnEarlyOnset_ForGivenPatientWomenSevenTriggerAgeLessThan30()
            throws ParseException {
        // GIVEN
        Patient patient = new Patient(
                1L,
                "Cartmanine",
                "Ericka",
                simpleDateFormat.parse("1998-08-02"),
                "F",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean history1 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        HistoryBean history2 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.");

        HistoryBean history3 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé");

        HistoryBean history4 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare qu'il est fumeur occasionnel");


        HistoryBean history5 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant une microalbumine élevée");

        HistoryBean history6 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant une perte de poids depuis une semaine");

        HistoryBean history7 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Fumeur, il a arrêté dans les 12 mois précédents");


        when(historyProxy.getAllHistoryByPatientId(history1.getPatientId())).thenReturn(
                List.of(history1, history2, history3, history4, history5, history6, history7));

        // WHEN
        Assessment response = assessmentService.getPatientAssessment(patient);

        // THEN
        assertThat(response.getAssessment()).isEqualTo("Apparition précoce (Early onset)");
    }


    @Test
    @DisplayName("getPatientAssessment should return patient assessment Early Onset")
    void getPatientAssessment_ShouldReturnEarlyOnset_ForGivenPatientEightTriggerAgeEqualOrGreaterThan30()
            throws ParseException {
        // GIVEN
        Patient patient = new Patient(
                1L,
                "Cartmanine",
                "Ericka",
                simpleDateFormat.parse("1978-08-02"),
                "F",
                "7 Rue Lucien Deneau",
                "999-444-9999");
        HistoryBean history1 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        HistoryBean history2 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.");

        HistoryBean history3 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé");

        HistoryBean history4 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière");


        HistoryBean history5 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant une microalbumine élevée");

        HistoryBean history6 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Tests de laboratoire indiquant une perte de poids depuis une semaine");

        HistoryBean history7 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Fumeur, il a arrêté dans les 12 mois précédents");

        HistoryBean history8 = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Le patient déclare qu'il a des maux de tête anormal depuis lundi");

        when(historyProxy.getAllHistoryByPatientId(history1.getPatientId())).thenReturn(
                List.of(history1, history2, history3,
                        history4, history5, history6, history7, history8));

        // WHEN
        Assessment response = assessmentService.getPatientAssessment(patient);

        // THEN
        assertThat(response.getAssessment()).isEqualTo("Apparition précoce (Early onset)");
    }



}