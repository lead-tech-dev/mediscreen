package com.mediscreen.clientui.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * The PatientBean class implements a bean of
 * Patient entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientBean {

    private Long id;

    @NotBlank
    private String family;

    @NotBlank
    private String given;

    @NotBlank
    private String dob;


    private String sex;

    private String address;

    private String phone;

    @Override
    public String toString() {
        return "PatientBean{" +
                "firstname='" + family + '\'' +
                ", lastname='" + given + '\'' +
                ", birthdate='" + dob + '\'' +
                ", genre='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phone + '\'' +
                '}';
    }
}
