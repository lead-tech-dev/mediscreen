package com.mediscreen.clientui.web.service;

/**
 * DateValidator. Functional Interface that
 * handle date validation.
 */
public interface DateValidator {

    /**
     * isValid. Method that check valid date.
     *
     * @param dateStr a date
     * @return boolean
     */
    boolean isValid(String dateStr);
}
