package com.symptomchecker.symptom_checker.model;

public class Disease {
    private String name;
    private String description;

    public Disease() {}

    public Disease(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
