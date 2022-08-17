package com.mediscreen.clientui.web.service.impl;

import com.mediscreen.clientui.bean.AssessmentBean;
import com.mediscreen.clientui.bean.PatientBean;
import com.mediscreen.clientui.proxies.AssessmentProxy;
import com.mediscreen.clientui.web.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AssessmentServiceImpl. class that implement
 * assessment business logic
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentProxy assessmentProxy;

    /**
     * {@inheritDoc}
     */
    @Override
    public AssessmentBean getPatientAssessment(PatientBean patientBean) {
        return assessmentProxy.getAssessment(patientBean);
    }
}
