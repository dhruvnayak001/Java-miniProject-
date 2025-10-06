package com.symptomchecker.symptom_checker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class KeepAliveController {

    // Custom endpoint just to keep the backend awake
    @GetMapping("/ping-server")
    public String ping() {
        return "Backend is alive!";
    }

    // Optional JSON version
    @GetMapping("/ping-json")
    public Map<String, String> pingJson() {
        return Map.of(
                "status", "alive",
                "timestamp", Instant.now().toString()
        );
    }
}
