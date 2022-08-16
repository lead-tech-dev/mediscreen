package com.mediscreen.assessment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * The Patient class implements a Patient
 * entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Long id;

    private String family;

    private String given;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String sex;

    private String address;

    private String phone;
}
