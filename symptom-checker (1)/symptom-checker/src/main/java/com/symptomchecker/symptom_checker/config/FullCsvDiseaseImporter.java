package com.symptomchecker.symptom_checker.config;

import com.symptomchecker.symptom_checker.model.Disease;
import com.symptomchecker.symptom_checker.repository.DiseaseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class FullCsvDiseaseImporter implements CommandLineRunner {

    private final DiseaseRepository repo;

    public FullCsvDiseaseImporter(DiseaseRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() > 0) {
            System.out.println("⚠️ Diseases already exist, skipping import.");
            return;
        }

        // Load from resources
        String descFile = "disease_description.csv";
        String precautionFile = "disease_precaution.csv";
        String severityFile = "symptom_severity.csv";
        String trainingFile = "dataset_sorted.csv";

        // 1️⃣ Load descriptions
        Map<String, String> descriptionMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource(descFile).getInputStream(), StandardCharsets.UTF_8))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    descriptionMap.put(parts[0].trim().toLowerCase(), parts[1].trim());
                }
            }
        }

        // 2️⃣ Load precautions
        Map<String, List<String>> precautionMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource(precautionFile).getInputStream(), StandardCharsets.UTF_8))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String disease = parts[0].trim().toLowerCase();
                    List<String> precs = new ArrayList<>();
                    for (int i = 1; i < parts.length; i++) {
                        if (!parts[i].trim().isEmpty()) precs.add(parts[i].trim());
                    }
                    precautionMap.put(disease, precs);
                }
            }
        }

        // 3️⃣ Load symptom severity
        Map<String, Integer> severityMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource(severityFile).getInputStream(), StandardCharsets.UTF_8))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    severityMap.put(parts[0].trim().toLowerCase(), Integer.parseInt(parts[1].trim()));
                }
            }
        }

        // 4️⃣ Load training dataset and merge
        // inside the "4️⃣ Load training dataset and merge" section
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource(trainingFile).getInputStream(), StandardCharsets.UTF_8))) {
            br.readLine(); // skip header
            String line;

            // track unique diseases to avoid accidental duplicates
            Set<String> processedDiseases = new HashSet<>();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String diseaseName = parts[0].trim();

                    // skip if already processed
                    if (processedDiseases.contains(diseaseName.toLowerCase())) {
                        continue;
                    }

                    Set<String> symptoms = new HashSet<>(); // use Set to avoid duplicates
                    Map<String, Integer> symptomSevMap = new HashMap<>();

                    for (int i = 1; i < parts.length; i++) {
                        if (!parts[i].trim().isEmpty()) {
                            String symptom = parts[i].trim();
                            symptoms.add(symptom);
                            symptomSevMap.put(symptom, severityMap.getOrDefault(symptom.toLowerCase(), 0));
                        }
                    }

                    String desc = descriptionMap.getOrDefault(diseaseName.toLowerCase(), "");
                    List<String> precs = precautionMap.getOrDefault(diseaseName.toLowerCase(), new ArrayList<>());

                    Disease disease = new Disease(
                            diseaseName,
                            desc,
                            new ArrayList<>(symptoms), // convert Set -> List
                            precs,
                            symptomSevMap
                    );

                    repo.save(disease);
                    processedDiseases.add(diseaseName.toLowerCase());
                }
            }
        }



        System.out.println("✅ All CSV data imported into MongoDB");
    }
}
