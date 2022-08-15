package com.mediscreen.clientui.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    /**
     * home. Method that display a home page.
     *
     * @return home view
     */

    @GetMapping("/")
    public String home() {
        log.info("Displaying home page!");
        return "home";
    }

}
