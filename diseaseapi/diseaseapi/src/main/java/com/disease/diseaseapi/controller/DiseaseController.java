package com.disease.diseaseapi.controller;
import com.disease.diseaseapi.model.Disease;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {

    private List<Disease> diseaseList = new ArrayList<>();

    public DiseaseController() {
        diseaseList.add(new Disease("Flu", "Fever, chills, cough"));
        diseaseList.add(new Disease("Diabetes", "High blood sugar"));
    }

    @GetMapping
    public List<Disease> getAllDiseases() {
        return diseaseList;
    }
    @PostMapping()
    public Disease addDisease(@RequestBody Disease disease) {
        diseaseList.add(disease);
        return disease;
    }

}
