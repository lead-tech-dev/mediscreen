package com.mediscreen.clientui.service;

import com.mediscreen.clientui.bean.AssessmentBean;
import com.mediscreen.clientui.bean.HistoryBean;
import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.proxies.AssessmentProxy;
import com.mediscreen.clientui.web.service.impl.AssessmentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("AssessmentServiceImplTest")
@DisplayName("assessment service implementation class")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AssessmentServiceImplTest {

    @Mock
    private AssessmentProxy assessmentProxy;

    @InjectMocks
    private AssessmentServiceImpl assessmentService;

    @Test
    @DisplayName("getPatientAssessment should return patient assessment")
    void getPatientAssessment_ShouldReturnPatientAssessment() throws ParseException {
        // GIVEN
        AssessmentBean assessment = new AssessmentBean(
                "cartman",
                "eric",
                41,
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        PatientBean patient = new PatientBean(
                1L,
                "Cartman",
                "Eric",
                "1981-02-23 06:41:05",
                "M",
                "7 Rue Lucien Deneau",
                "999-444-9999");

        when(assessmentProxy.getAssessment(patient)).thenReturn(assessment);

        // WHEN
        AssessmentBean response = assessmentService.getPatientAssessment(patient);

        // THEN
        verify(assessmentProxy, times(1)).getAssessment(patient);
        assertThat(response).isEqualTo(assessment);
    }

}