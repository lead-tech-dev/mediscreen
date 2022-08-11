package com.mediscreen.clientui.service;

import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.proxies.PatientProxy;
import com.mediscreen.clientui.web.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientProxy patientProxy;

    @Override
    public Map<String, Object> getAllPatient(int pageNumber) {
        return patientProxy.getAllPatient(pageNumber);
    }

    @Override
    public PatientBean addPatient(PatientBean patientBean) {
        return patientProxy.addPatient(patientBean);
    }

    @Override
    public PatientBean getPatient(long id) {
        return patientProxy.getPatient(id);
    }

    @Override
    public PatientBean updatePatient(PatientBean patientBean) {
        return patientProxy.updatePatient(patientBean);
    }

    @Override
    public void deletePatient(long id) {
        patientProxy.deletePatient(id);
    }
}
