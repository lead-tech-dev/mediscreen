package com.mediscreen.clientui.web.service.impl;

import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.proxies.PatientProxy;
import com.mediscreen.clientui.web.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * PatientServiceImpl. class that implement
 * patient business logic
 */
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientProxy patientProxy;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getAllPatient(int pageNumber) {
        return patientProxy.getAllPatient(pageNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientBean addPatient(PatientBean patientBean) {
        return patientProxy.addPatient(patientBean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientBean getPatient(long id) {
        return patientProxy.getPatient(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientBean updatePatient(PatientBean patientBean) {
        return patientProxy.updatePatient(patientBean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePatient(long id) {
        patientProxy.deletePatient(id);
    }
}
