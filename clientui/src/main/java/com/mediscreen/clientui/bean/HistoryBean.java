package com.mediscreen.clientui.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * The HistoryBean class implements a bean of
 * history entity.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryBean {
    private String id;

    private long patientId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotBlank
    private String note;
}
