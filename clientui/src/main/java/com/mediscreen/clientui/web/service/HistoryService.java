package com.mediscreen.clientui.web.service;

import com.mediscreen.clientui.bean.HistoryBean;
import com.mediscreen.clientui.bean.PatientBean;

import java.util.List;
import java.util.Map;

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
     * @return a list of patient historyBean
     */
    List<HistoryBean> getAllHistoryPatientById(long id);


    /**
     * addHistory. Method that add given historyBean in database.
     *
     * @param historyBean a historyBean
     * @return a historyBean
     */
    HistoryBean addHistory(HistoryBean historyBean);

    /**
     * getHistory. Method that get one historyBean from database.
     *
     * @param id a historyBean id
     * @return historyBean
     */
    HistoryBean getHistory(String id);

    /**
     * updateHistory. Method that update given history
     * in database.
     *
     * @param historyBean a historyBean
     * @return HistoryBean
     */
    HistoryBean updateHistory(HistoryBean historyBean);

    /**
     * deleteHistory. Method that delete given historyBean id in database.
     *
     * @param id a historyBean id
     */
    void deleteHistory(String id);
}
