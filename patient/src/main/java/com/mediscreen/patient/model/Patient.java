package com.mediscreen.patient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The Patient class implements a Patient
 * entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "please provide family name")
    private String family;

    @NotBlank(message = "please provide given name")
    private String given;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "please provide dob")
    private Date dob;

    @NotBlank(message = "please provide sex")
    private String sex;

    private String address;

    private String phone;
}
