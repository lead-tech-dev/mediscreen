package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.bean.HistoryBean;
import com.mediscreen.clientui.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * HistoryProxy. Interface that manage history
 * microservice endPoints.
 *
 */
@FeignClient(name = "history", url = "${history.service.url}")
public interface HistoryProxy {

    /**
     * addHistory. Method that add given patient in database.
     *
     * @param historyBean a historyBean
     * @return an HistoryBean
     */
    @PostMapping( value = "/history/add")
    HistoryBean addHistory(@RequestBody HistoryBean historyBean);

    /**
     * getAllHistoryByPatientId. Method that get history by patient id
     * from database.
     *
     * @param id a patient id
     * @return a patient history list
     */
    @GetMapping( value = "/history/getAll/{id}")
    Iterable<HistoryBean> getAllHistoryByPatientId(@PathVariable("id") long id);

    /**
     * getHistory. Method that get one history from database.
     *
     * @param id a history id
     * @return HistoryBean
     */
    @GetMapping( value = "/history/get/{id}")
    HistoryBean getHistory(@PathVariable("id") String id);

    /**
     * updateHistory. Method that update given history
     * in database.
     *
     * @param historyBean a history
     * @return HistoryBean
     */
    @PutMapping( value = "/history/update")
    HistoryBean updateHistory(@RequestBody  HistoryBean historyBean);

    /**
     * deleteHistory. Method that delete given history id in database.
     *
     * @param id a history id
     */
    @DeleteMapping( value = "/history/delete/{id}")
    HistoryBean deleteHistory(@PathVariable("id") String id);
}
