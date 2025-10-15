package com.symptomchecker.symptom_checker.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*")
public class NewsController {

    private final String API_KEY = "0603a2c3b2c14251a0fef2daeea3bd01";
    private final RestTemplate restTemplate = new RestTemplate();

    private String cachedDate = "";
    private List<Map<String, Object>> cachedArticles = new ArrayList<>();

    @GetMapping("/daily")
    public List<Map<String, Object>> getDailyNews() {
        String today = LocalDate.now().toString();

        if (!today.equals(cachedDate)) {
            // Preferred: Use top-headlines with health category for best results
            String url = "https://newsapi.org/v2/top-headlines?" +
                    "category=health" +
                    "&language=en" +
                    "&country=in" +  // You can change to "us" or remove for global
                    "&pageSize=7" +
                    "&apiKey=" + API_KEY;

            Map response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> articles = (List<Map<String, Object>>) response.get("articles");

            cachedArticles = articles != null ? articles : new ArrayList<>();
            cachedDate = today;

            // If no articles found, fallback to /everything with strict keywords
            if (cachedArticles.isEmpty()) {
                url = "https://newsapi.org/v2/everything?" +
                        "q=health AND medical AND hospital" +
                        "&language=en" +
                        "&pageSize=7" +
                        "&sortBy=publishedAt" +
                        "&apiKey=" + API_KEY;
                response = restTemplate.getForObject(url, Map.class);
                articles = (List<Map<String, Object>>) response.get("articles");
                cachedArticles = articles != null ? articles : new ArrayList<>();
            }
        }

        return cachedArticles;
    }
}
