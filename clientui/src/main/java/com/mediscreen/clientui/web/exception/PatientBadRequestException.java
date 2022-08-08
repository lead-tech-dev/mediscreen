package com.mediscreen.clientui.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * PatientBadRequestException. Class that handle bad request
 * exception.
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PatientBadRequestException extends RuntimeException
{
    public PatientBadRequestException(String msg) {
        super(msg);
    }
}
