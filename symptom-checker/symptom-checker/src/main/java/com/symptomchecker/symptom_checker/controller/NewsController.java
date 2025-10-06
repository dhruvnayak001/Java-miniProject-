package com.symptomchecker.symptom_checker.controller;

import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*")
public class NewsController {

    @GetMapping("/daily")
    public Map<String, String> healthCheck() {
        // This replaces the news API functionality
        return Map.of(
                "status", "alive",
                "timestamp", Instant.now().toString()
        );
    }
}
