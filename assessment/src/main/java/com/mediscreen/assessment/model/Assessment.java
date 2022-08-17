package com.mediscreen.assessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The Assessment class implements a assessment
 * entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Assessment {
    private String family;

    private String given;

    private int birthdate;

    private String assessment;

}
