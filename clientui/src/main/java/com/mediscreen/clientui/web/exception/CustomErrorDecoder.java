package com.mediscreen.clientui.web.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * CustomErrorDecoder. Class that implement ErrorDecoder Interface
 * dedicated to decoding the http response
 */
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    /**
     * {@inheritDoc}
     */
    @Override
    public Exception decode(String invoqueur, Response reponse) {
        if(reponse.status() == 400 ) {
            return new PatientBadRequestException(
                    "Requête incorrecte "
            );
        }
        return defaultErrorDecoder.decode(invoqueur, reponse);
    }
}
