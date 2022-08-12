package com.mediscreen.history.web.controller;

import com.mediscreen.history.model.History;
import com.mediscreen.history.web.exception.BadRequestException;
import com.mediscreen.history.web.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * HistoryController. class that implement
 * request/response logic of history.
 */
@RestController
@RequestMapping("/history")
public class HistoryController {

    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Autowired
    private HistoryService historyService;

    /**
     * getAllHistory. Method that get  list
     * of patient histories by id.
     *
     * @return list of history
     */
    @GetMapping("/getAll/{id}")
    public Iterable<History> getAllHistory(@PathVariable("id") long id) {

        Iterable<History> histories = historyService.getAllHistoryByPatientId(id);

        log.info("Getting history list : {}", histories);

        return histories;
    }


    /**
     * getHistory. Method that get one history.
     *
     * @param id a given history id
     * @return History object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<History> getHistory(@PathVariable("id") String id) {

        log.info("Getting history with id {}", id);

        Optional<History> history = historyService.getHistory(id);

        if (history.isEmpty()) {

            log.error("Bad request exception: History not found!");

            throw new BadRequestException("History not found!");
        }

        log.info("Getting history request success. History getting  data: {}",
                history.get());

        return new ResponseEntity<>(history.get(), HttpStatus.OK);
    }

    /**
     * addHistory. Method that add new history.
     *
     * @param history a given history
     * @return History object
     */
    @PostMapping("/add")
    public ResponseEntity<History> addHistory(@RequestBody History history) {

        log.info("Creating history with data: {}", history);

        History added = historyService.addHistory(history);

        log.info("Save history request success. Patient saved with data {}",
                history);

        return new ResponseEntity<>(added, HttpStatus.OK);
    }

    /**
     * updateHistory. Method that update a history.
     *
     * @param history a given patient
     * @return History object
     */
    @PutMapping("/update")
    public ResponseEntity<History> updateHistory(@RequestBody History history) {

        log.info("Updating history with data {}", history);

        Optional<History> exist = historyService.getHistory(history.getId());

        if (exist.isEmpty()) {

            log.error("Bad request exception: History not found!");

            throw new BadRequestException("History not found!");
        }

        History updated = historyService.updateHistory(history);

        log.info("Update history request success. History updated with data {}",
                history);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * deleteHistory. Method that delete a history.
     *
     * @param id a given history id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable("id") String id) {

        log.info("Deleting history with id {}", id);

        Optional<History> exist = historyService.getHistory(id);

        if (exist.isEmpty()) {

            log.error("History not exists with id {}", id);

            throw new BadRequestException("History not found!");
        }

        historyService.deleteHistory(id);

        log.info("Delete history request success with id {}", id);

        return ResponseEntity.noContent().build();
    }
}
