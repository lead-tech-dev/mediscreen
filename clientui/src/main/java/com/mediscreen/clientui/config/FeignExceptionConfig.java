package com.mediscreen.clientui.config;

import com.mediscreen.clientui.web.exception.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignExceptionConfig. class that handle Feign
 * exception configuration.
 */
@Configuration
public class FeignExceptionConfig {

    /**
     * mCustomErrorDecoder. a bean that can replace
     * default customErrorDecoder
     */
    @Bean
    public CustomErrorDecoder mCustomErrorDecoder(){
        return new CustomErrorDecoder();
    }
}