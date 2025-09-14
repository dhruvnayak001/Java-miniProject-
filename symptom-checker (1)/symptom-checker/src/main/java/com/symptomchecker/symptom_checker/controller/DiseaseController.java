package com.symptomchecker.symptom_checker.controller;

import com.symptomchecker.symptom_checker.model.Disease;
import com.symptomchecker.symptom_checker.service.DiseaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DiseaseController {

    private final DiseaseService service;

    public DiseaseController(DiseaseService service) {
        this.service = service;
    }

    @GetMapping("/match")
    public List<Disease> matchDiseases(@RequestParam List<String> symptoms) {
        List<String> flatSymptoms = symptoms.stream()
                .flatMap(s -> List.of(s.split(",")).stream())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return service.matchBySymptoms(flatSymptoms);
    }



    @PostMapping("/diseases")
    public Disease addDisease(@RequestBody Disease disease) {
        return service.addDisease(disease);
    }

    @GetMapping("/diseases")
    public List<Disease> getAllDiseases() {
        return service.getAllDiseases();
    }
}