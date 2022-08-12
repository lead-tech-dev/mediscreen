package com.mediscreen.clientui.web.service.impl;

import com.mediscreen.clientui.web.service.DateValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * DateValidatorUsingDateFormat. Class that implement
 * DateValidator.
 */
public class DateValidatorUsingDateFormat implements DateValidator {
    private String dateFormat;

    /**
     * DateValidatorUsingDateFormat a constructor
     * @param dateFormat date format
     */
    public DateValidatorUsingDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(this.dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
