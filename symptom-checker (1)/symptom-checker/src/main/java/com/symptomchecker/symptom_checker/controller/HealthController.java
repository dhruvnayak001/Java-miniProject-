package com.symptomchecker.symptom_checker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/healthz")
    public String healthCheck() {
        return "OK";
    }
}
