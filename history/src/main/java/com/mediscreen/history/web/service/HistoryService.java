package com.mediscreen.history.web.service;

import com.mediscreen.history.model.History;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * HistoryService interface that structure the business logic
 * of history.
 *
 */
public interface HistoryService {

    /**
     * getAllHistoryByPatientId. Method that get history
     * by patient id.
     *
     * @param id a patient id
     * @return a list of patient history
     */
    List<History> getAllHistoryByPatientId(long id);

    /**
     * getHistory. Method that get one history from database.
     *
     * @param id a history id
     * @return history
     */
    Optional<History> getHistory(String id);

    /**
     * addHistory. Method that add given history in database.
     *
     * @param history a patient
     * @return a history
     */
    History addHistory(History history);

    /**
     * updateHistory. Method that update given history
     * in database.
     *
     * @param history a history
     * @return History
     */
    History updateHistory(History history);

    /**
     * deleteHistory. Method that delete given history id in database.
     *
     * @param id a history id
     */
    void deleteHistory(String id);
}
