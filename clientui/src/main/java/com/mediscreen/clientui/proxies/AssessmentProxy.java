package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.bean.AssessmentBean;
import com.mediscreen.clientui.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AssessmentProxy. Interface that manage assessment
 * microservice endPoints.
 *
 */
@FeignClient(name = "assessment", url = "${assessment.service.url}")
public interface AssessmentProxy {

    /**
     * getAssessment. Method that get patient assessment.
     *
     * @param patientBean an patientBean
     * @return an AssessmentBean
     */
    @PostMapping( value = "/assess")
    AssessmentBean getAssessment(@RequestBody PatientBean patientBean);
}
