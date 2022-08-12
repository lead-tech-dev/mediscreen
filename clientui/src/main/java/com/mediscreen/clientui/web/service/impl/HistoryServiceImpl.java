package com.mediscreen.clientui.web.service.impl;

import com.mediscreen.clientui.bean.HistoryBean;
import com.mediscreen.clientui.proxies.HistoryProxy;
import com.mediscreen.clientui.web.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * HistoryServiceImpl. class that implement
 * history business logic
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryProxy historyProxy;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<HistoryBean> getAllHistoryPatientById(long id) {
        return historyProxy.getAllHistoryByPatientId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryBean getHistory(String id) {
        return historyProxy.getHistory(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryBean addHistory(HistoryBean historyBean) {
        return historyProxy.addHistory(historyBean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryBean updateHistory(HistoryBean historyBean) {
        return historyProxy.updateHistory(historyBean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteHistory(String id) {
        historyProxy.deleteHistory(id);
    }
}
