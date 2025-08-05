package com.symptomchecker.symptom_checker.controller;

import com.symptomchecker.symptom_checker.model.Disease;
import com.symptomchecker.symptom_checker.service.DiseaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*") // Allow frontend access
@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {
    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @GetMapping
    public List<Disease> getAllDiseases() {
        return diseaseService.getAllDiseases();
    }

    @PostMapping("/match")
    public List<Disease> matchDiseases(@RequestBody List<String> symptoms) {
        return diseaseService.matchDiseases(symptoms);
    }
    @PostMapping
    public Disease addDisease(@RequestBody Disease disease) {
        return diseaseService.addDisease(disease);
    }


}
