package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "patient", url = "localhost:8081")
public interface PatientProxy {
    @PostMapping( value = "/patient/add")
    PatientBean addPatient(@RequestBody PatientBean patientBean);
}
