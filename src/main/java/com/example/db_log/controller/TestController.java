package com.example.db_log.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    /**
     * Serves the main test panel page.
     */
    @GetMapping("/")
    public String index() {
        return "index"; // This will resolve to /resources/templates/index.html
    }
}
