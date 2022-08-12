package com.mediscreen.history.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * The History class implements a history
 * entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "history")
public class History {

    @Id
    private String id;

    @NotNull
    private long patientId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotBlank
    private String note;
}
