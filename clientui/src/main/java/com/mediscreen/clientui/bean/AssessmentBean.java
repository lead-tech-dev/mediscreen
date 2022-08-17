package com.mediscreen.clientui.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The AssessmentBean class implements a bean of
 * assessment object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentBean {
    private String family;

    private String given;

    private int birthdate;

    private String assessment;
}
