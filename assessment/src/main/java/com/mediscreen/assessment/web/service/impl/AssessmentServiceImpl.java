package com.mediscreen.assessment.web.service.impl;

import com.mediscreen.assessment.bean.HistoryBean;
import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.model.Patient;
import com.mediscreen.assessment.proxies.HistoryProxy;
import com.mediscreen.assessment.web.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/**
 * AssessmentServiceImpl. class that implement
 * assessment business logic
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {
    @Autowired
    private HistoryProxy historyProxy;

    /**
     * {@inheritDoc}
     */
    @Override
    public Assessment getPatientAssessment(Patient patient) {
        List<HistoryBean> patientHistory = historyProxy.getAllHistoryByPatientId(patient.getId());
        int age = getAge(patient.getDob());
        Assessment assessment = new Assessment(
                patient.getFamily(),
                patient.getGiven(),
                age,
                ""
        );

        int triggersNumber = getTriggersNumber(patientHistory);
        boolean women = getWomen(patient.getSex());
        boolean men = getMen(patient.getSex());

        System.out.println("triggersNumber: " +triggersNumber);
        System.out.println("women: " +women);
        System.out.println("Men: " +men);
        System.out.println("Age: " +age);

       if (triggersNumber == 2 && age > 30 ) {
            assessment.setAssessment("Risque limité (Borderline)");
        } else  if (women && triggersNumber == 4
                    || men && triggersNumber == 3
                    || age > 30 && triggersNumber == 6) {
                assessment.setAssessment("Danger (In Danger)");
        } else if (men && age < 30 && triggersNumber == 5
                   || women && age < 30 && triggersNumber == 7 || age > 30 && triggersNumber >= 8) {
               assessment.setAssessment("Apparition précoce (Early onset)");
       }
       else {
           assessment.setAssessment("Aucun risque (None)");
        }

        return assessment;
    }

    private int getTriggersNumber(List<HistoryBean> histories) {
        int cptTrigger = 0;
        List<String> triggers = List.of(
                "hémoglobine A1C",
                "Microalbumine",
                "Taille",
                "Poids",
                "Fumeur",
                "Fumer",
                "Anormal",
                "Anormale",
                "Cholestérol",
                "Vertige"
        );

        for (HistoryBean historyBean: histories) {
            for (String trigger: triggers) {
                cptTrigger += historyBean.getNote().toLowerCase().split(trigger.toLowerCase()).length - 1;
            }
        }
        return cptTrigger;
    }

    private int getAge(Date birthdate) {
        Date curDate = Date.from(Instant.now());
        return curDate.getYear() - birthdate.getYear();
    }

    private boolean getWomen(String sex) {
        return Objects.equals(sex, "F");
    }

    private boolean getMen(String sex) {
        return Objects.equals(sex, "M");
    }

}
