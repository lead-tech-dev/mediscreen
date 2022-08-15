package com.mediscreen.clientui.service;

import com.mediscreen.clientui.bean.HistoryBean;
import com.mediscreen.clientui.proxies.HistoryProxy;
import com.mediscreen.clientui.proxies.PatientProxy;
import com.mediscreen.clientui.web.service.impl.HistoryServiceImpl;
import com.mediscreen.clientui.web.service.impl.PatientServiceImpl;
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
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("HistoryServiceImplTest")
@DisplayName("history service implementation class")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class HistoryServiceImplTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Mock
    private HistoryProxy historyProxy;

    @InjectMocks
    private HistoryServiceImpl historyService;

    @Test
    @DisplayName("getAllHistoryPatientById should return patient history list")
    void getAllHistoryPatientById_ShouldReturnPatientHistoryList() throws ParseException {
        // GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        when(historyProxy.getAllHistoryByPatientId(1)).thenReturn(List.of(historyBean));

        // WHEN
        List<HistoryBean> response = historyService.getAllHistoryPatientById(1);

        // THEN
        verify(historyProxy, times(1)).getAllHistoryByPatientId(1);
        assertTrue(response.contains(historyBean));
    }

    @Test
    @DisplayName("getHistory should return history for given history id")
    void getHistory_ShouldReturnHistory_ForGivenHistoryId() throws ParseException {
        // GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        when(historyProxy.getHistory(historyBean.getId())).thenReturn(historyBean);

        // WHEN
        HistoryBean response = historyService.getHistory(historyBean.getId());

        // THEN
        verify(historyProxy, times(1)).getHistory(historyBean.getId());
        assertThat(response).isEqualTo(historyBean);
    }

    @Test
    @DisplayName("addHistory should return added history for given history")
    void addHistory_ShouldReturnAddedHistory_ForGivenHistory() throws ParseException {
        // GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        when(historyProxy.addHistory(historyBean)).thenReturn(historyBean);

        // WHEN
        HistoryBean response = historyService.addHistory(historyBean);

        // THEN
        verify(historyProxy, times(1)).addHistory(historyBean);
        assertThat(response).isEqualTo(historyBean);
    }

    @Test
    @DisplayName("updateHistory should return updated history for given history")
    void updateHistory_ShouldReturnUpdatedHistory_ForGivenHistory() throws ParseException {
        // GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        when(historyProxy.updateHistory(historyBean)).thenReturn(historyBean);

        // WHEN
        HistoryBean response = historyService.updateHistory(historyBean);

        // THEN
        verify(historyProxy, times(1)).updateHistory(historyBean);
        assertThat(response).isEqualTo(historyBean);
    }

    @Test
    @DisplayName("deleteHistory should delete history for given history id")
    void deleteHistory_ShouldDeleteHistory_ForGivenHistoryId() throws ParseException {
        // GIVEN
        HistoryBean historyBean = new HistoryBean(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        when(historyProxy.deleteHistory(historyBean.getId())).thenReturn(null);

        // WHEN
        historyService.deleteHistory(historyBean.getId());

        // THEN
        verify(historyProxy, times(1)).deleteHistory(historyBean.getId());

    }
}