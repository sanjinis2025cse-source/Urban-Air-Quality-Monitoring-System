package com.example.urban_air_quality.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Serves the Thymeleaf monitoring workspace. */
@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
