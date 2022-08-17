package com.mediscreen.assessment.proxies;

import com.mediscreen.assessment.bean.HistoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HistoryProxy. Interface that manage history
 * microservice endPoints.
 *
 */


@FeignClient(name = "history", url = "${history.service.url}")
public interface HistoryProxy {

    /**
     * getAllHistoryByPatientId. Method that get history by patient id
     * from database.
     *
     * @param id a patient id
     * @return a patient history list
     */
    @GetMapping( value = "/history/getAll/{id}")
    List<HistoryBean> getAllHistoryByPatientId(@PathVariable("id") long id);


}
