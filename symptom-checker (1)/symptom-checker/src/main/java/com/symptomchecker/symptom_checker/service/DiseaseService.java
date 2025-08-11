package com.symptomchecker.symptom_checker.service;

import com.symptomchecker.symptom_checker.model.Disease;
import com.symptomchecker.symptom_checker.repository.DiseaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseService {
    private final DiseaseRepository repository;

    public DiseaseService(DiseaseRepository repository) {
        this.repository = repository;
    }

    public List<Disease> getAllDiseases() {
        return repository.findAll();
    }

    public List<Disease> matchBySymptoms(List<String> symptoms) {
        List<Disease> allDiseases = repository.findAll();

        List<String> lowerSymptoms = symptoms.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return allDiseases.stream()
                .filter(disease -> disease.getSymptoms().stream()
                        .map(String::toLowerCase)
                        .anyMatch(symptomFromDb ->
                                lowerSymptoms.stream()
                                        .anyMatch(inputSymptom -> symptomFromDb.contains(inputSymptom))
                        )
                )
                .toList();
    }



    public Disease addDisease(Disease disease) {
        return repository.save(disease);
    }
}
