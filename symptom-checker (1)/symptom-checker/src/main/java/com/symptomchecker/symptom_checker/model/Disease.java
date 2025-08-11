package com.symptomchecker.symptom_checker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "diseases")
public class Disease {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> symptoms;
    private List<String> precautions; // new field
    private Map<String, Integer> symptomSeverity; // symptom : severity score

    public Disease() {}

    public Disease(String name, String description, List<String> symptoms, List<String> precautions, Map<String, Integer> symptomSeverity) {
        this.name = name;
        this.description = description;
        this.symptoms = symptoms;
        this.precautions = precautions;
        this.symptomSeverity = symptomSeverity;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getSymptoms() { return symptoms; }
    public void setSymptoms(List<String> symptoms) { this.symptoms = symptoms; }

    public List<String> getPrecautions() { return precautions; }
    public void setPrecautions(List<String> precautions) { this.precautions = precautions; }

    public Map<String, Integer> getSymptomSeverity() { return symptomSeverity; }
    public void setSymptomSeverity(Map<String, Integer> symptomSeverity) { this.symptomSeverity = symptomSeverity; }
}
