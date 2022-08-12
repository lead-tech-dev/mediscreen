package com.mediscreen.history.service;

import com.mediscreen.history.model.History;
import com.mediscreen.history.repository.HistoryRepository;
import com.mediscreen.history.web.service.impl.HistoryServiceImpl;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Tag("HistoryServiceImplTest")
@DisplayName("history service implementation class")
class HistoryServiceImplTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryServiceImpl historyService;

    @Test
    @DisplayName("getAllHistoryPatientById should return patient history list")
    void getAllHistoryPatientById_ShouldReturnPatientHistoryList() throws ParseException {
        // GIVEN
        History History = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");

        when(historyRepository.findAllByPatientId(1)).thenReturn(List.of(History));

        // WHEN
        List<History> response = historyService.getAllHistoryByPatientId(1);

        // THEN
        verify(historyRepository, times(1)).findAllByPatientId(1);
        assertTrue(response.contains(History));
    }

    @Test
    @DisplayName("getHistory should return history for given history id")
    void getHistory_ShouldReturnHistory_ForGivenHistoryId() throws ParseException {
        // GIVEN
        History History = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        when(historyRepository.findById(History.getId())).thenReturn(Optional.of(History));

        // WHEN
        Optional<History> response = historyService.getHistory(History.getId());

        // THEN
        verify(historyRepository, times(1)).findById(History.getId());
        assertThat(response.get()).isEqualTo(History);
    }

    @Test
    @DisplayName("addHistory should return added history for given history")
    void addHistory_ShouldReturnAddedHistory_ForGivenHistory() throws ParseException {
        // GIVEN
        History History = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        when(historyRepository.save(History)).thenReturn(History);

        // WHEN
        History response = historyService.addHistory(History);

        // THEN
        verify(historyRepository, times(1)).save(History);
        assertThat(response).isEqualTo(History);
    }

    @Test
    @DisplayName("updateHistory should return updated history for given history")
    void updateHistory_ShouldReturnUpdatedHistory_ForGivenHistory() throws ParseException {
        // GIVEN
        History History = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        when(historyRepository.save(History)).thenReturn(History);

        // WHEN
        History response = historyService.updateHistory(History);

        // THEN
        verify(historyRepository, times(1)).save(History);
        assertThat(response).isEqualTo(History);
    }

    @Test
    @DisplayName("deleteHistory should delete history for given history id")
    void deleteHistory_ShouldDeleteHistory_ForGivenHistoryId() throws ParseException {
        // GIVEN
        History History = new History(
                "618273e087def21060318688",
                1L,
                simpleDateFormat.parse("2021-11-03T23:00:00.518Z"),
                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level");
        doNothing().when(historyRepository).deleteById(History.getId());

        // WHEN
        historyService.deleteHistory(History.getId());

        // THEN
        verify(historyRepository, times(1)).deleteById(History.getId());

    }
}