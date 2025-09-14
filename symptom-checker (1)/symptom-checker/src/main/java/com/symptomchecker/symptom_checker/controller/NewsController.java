package com.symptomchecker.symptom_checker.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*")
public class NewsController {

    private final String API_KEY = "0603a2c3b2c14251a0fef2daeea3bd01"; // <-- put your key here
    private final RestTemplate restTemplate = new RestTemplate();

    private String cachedDate = "";
    private List<Map<String, Object>> cachedArticles = new ArrayList<>();

    @GetMapping("/daily")
    public List<Map<String, Object>> getDailyNews() {
        String today = LocalDate.now().toString();

        if (!today.equals(cachedDate)) {
            // 🩺 Filter by medical/health keywords
            String url = "https://newsapi.org/v2/everything?" +
                    "q=(health OR medical OR disease OR medicine OR hospital OR covid)" +
                    "&language=en" +
                    "&pageSize=7" +
                    "&sortBy=publishedAt" +
                    "&apiKey=" + API_KEY;

            Map response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> articles = (List<Map<String, Object>>) response.get("articles");

            cachedArticles = articles != null ? articles : new ArrayList<>();
            cachedDate = today;
        }

        return cachedArticles;
    }
}
