package com.mediscreen.clientui.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Tag("HomeControllerTest")
@DisplayName("home controller test logic")
class HomeControllerTest extends AbstractTest{

    @Test
    @DisplayName("home should display home page")
    void home_shouldDisplayHomePage() throws Exception {
        // GIVEN
        String uri = "/";

        // WHEN
        // THEN
        this.mvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(view().name("home"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}