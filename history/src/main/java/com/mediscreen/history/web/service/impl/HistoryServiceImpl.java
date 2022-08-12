package com.mediscreen.history.web.service.impl;

import com.mediscreen.history.model.History;
import com.mediscreen.history.repository.HistoryRepository;
import com.mediscreen.history.web.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * HistoryServiceImpl. class that implement
 * history business logic
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<History> getAllHistoryByPatientId(long id) {
        return historyRepository.findAllByPatientId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<History> getHistory(String id) {
        return historyRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public History addHistory(History history) {
        return historyRepository.save(history);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public History updateHistory(History history) {
        return historyRepository.save(history);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteHistory(String id) {
        historyRepository.deleteById(id);
    }
}
