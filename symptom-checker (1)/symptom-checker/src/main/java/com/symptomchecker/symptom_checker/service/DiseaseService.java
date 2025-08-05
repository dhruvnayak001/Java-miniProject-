package com.symptomchecker.symptom_checker.service;

import com.symptomchecker.symptom_checker.model.Disease;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseService {
    private final List<Disease> diseases = new ArrayList<>();

    public DiseaseService() {
        diseases.add(new Disease("Flu", "Fever and cough"));
        diseases.add(new Disease("Covid", "Fever, cough, breathlessness"));
        diseases.add(new Disease("Malaria", "Fever and chills"));
        diseases.add(new Disease("Malhar Syndrome", "Symptoms: Having an absurdly big cock"));
        diseases.add(new Disease("Mohit Disorder", "Symptoms: Excessive intelligence and dangerous brain usage"));
        diseases.add(new Disease("Dhruv Affliction", "Symptoms: Has a girlfriend, permanent smile, uncurable"));
        diseases.add(new Disease("Influenza", "Fever, cough, sore throat, runny nose"));
        diseases.add(new Disease("Common Cold", "Sneezing, stuffy nose, sore throat"));
        diseases.add(new Disease("Covid-19", "Fever, cough, breathlessness, fatigue, loss of taste"));
        diseases.add(new Disease("Malaria", "Fever, chills, headache, sweating"));
        diseases.add(new Disease("Dengue", "High fever, rash, muscle pain, joint pain"));
        diseases.add(new Disease("Typhoid", "Fever, weakness, stomach pain, headache"));
        diseases.add(new Disease("Tuberculosis", "Chronic cough, weight loss, fever, night sweats"));
        diseases.add(new Disease("Pneumonia", "Cough with phlegm, fever, chills, chest pain"));
        diseases.add(new Disease("Asthma", "Wheezing, breathlessness, chest tightness, coughing"));
        diseases.add(new Disease("Bronchitis", "Cough, mucus, fatigue, shortness of breath"));
        diseases.add(new Disease("Chikungunya", "Fever, joint pain, rash, headache"));
        diseases.add(new Disease("Hypertension", "Headache, vision problems, fatigue, chest pain"));
        diseases.add(new Disease("Diabetes", "Frequent urination, thirst, weight loss, fatigue"));
        diseases.add(new Disease("Heart Attack", "Chest pain, shortness of breath, nausea"));
        diseases.add(new Disease("Stroke", "Sudden numbness, confusion, vision loss, difficulty walking"));
        diseases.add(new Disease("Hepatitis A", "Fever, fatigue, nausea, jaundice"));
        diseases.add(new Disease("Hepatitis B", "Abdominal pain, dark urine, jaundice, fatigue"));
        diseases.add(new Disease("Hepatitis C", "Fatigue, joint pain, jaundice, appetite loss"));
        diseases.add(new Disease("Appendicitis", "Lower right abdominal pain, nausea, vomiting, fever"));
        diseases.add(new Disease("Chickenpox", "Itchy rash, fever, tiredness, headache"));
        diseases.add(new Disease("Measles", "Fever, cough, runny nose, skin rash"));
        diseases.add(new Disease("Mumps", "Swollen glands, fever, headache, muscle aches"));
        diseases.add(new Disease("Rubella", "Mild fever, rash, swollen lymph nodes"));
        diseases.add(new Disease("Polio", "Muscle weakness, fever, sore throat, paralysis"));
        diseases.add(new Disease("Scabies", "Severe itching, rash, sores, blisters"));
        diseases.add(new Disease("Ringworm", "Red circular rash, itching, hair loss"));
        diseases.add(new Disease("Sinusitis", "Facial pain, nasal congestion, headache"));
        diseases.add(new Disease("Conjunctivitis", "Red eyes, itchiness, discharge, tearing"));
        diseases.add(new Disease("Otitis Media", "Ear pain, fever, hearing loss"));
        diseases.add(new Disease("Tonsillitis", "Sore throat, difficulty swallowing, swollen tonsils"));
        diseases.add(new Disease("Urinary Tract Infection", "Burning urination, frequent urge, cloudy urine"));
        diseases.add(new Disease("Kidney Stones", "Severe pain, blood in urine, nausea"));
        diseases.add(new Disease("Gastritis", "Stomach pain, nausea, bloating, vomiting"));
        diseases.add(new Disease("Peptic Ulcer", "Abdominal pain, heartburn, nausea"));
        diseases.add(new Disease("Irritable Bowel Syndrome", "Abdominal cramps, bloating, diarrhea or constipation"));
        diseases.add(new Disease("Food Poisoning", "Vomiting, diarrhea, stomach cramps, fever"));
        diseases.add(new Disease("Migraine", "Severe headache, nausea, light sensitivity"));
        diseases.add(new Disease("Epilepsy", "Seizures, confusion, staring, jerking movements"));
        diseases.add(new Disease("Anemia", "Fatigue, pale skin, dizziness, shortness of breath"));
        diseases.add(new Disease("Depression", "Sadness, fatigue, loss of interest, sleep issues"));
        diseases.add(new Disease("Anxiety Disorder", "Nervousness, restlessness, fast heartbeat"));
        diseases.add(new Disease("Hypothyroidism", "Fatigue, weight gain, dry skin, depression"));
        diseases.add(new Disease("Hyperthyroidism", "Weight loss, irritability, tremors, rapid heartbeat"));
        diseases.add(new Disease("Psoriasis", "Red patches of skin, dry scales, itching"));
        diseases.add(new Disease("Eczema", "Itchy, inflamed skin, rash"));
        diseases.add(new Disease("Lupus", "Joint pain, rash, fatigue, fever"));
        diseases.add(new Disease("Celiac Disease", "Bloating, diarrhea, weight loss, fatigue"));
        diseases.add(new Disease("Parkinson’s Disease", "Tremor, stiffness, balance issues, slow movement"));
        diseases.add(new Disease("Alzheimer’s Disease", "Memory loss, confusion, mood changes, trouble speaking"));

    }


    public List<Disease> getAllDiseases() {
        return diseases;
    }

    public List<Disease> matchDiseases(List<String> symptoms) {
        return diseases.stream()
                .map(disease -> {
                    long matchCount = symptoms.stream()
                            .filter(symptom -> disease.getDescription().toLowerCase().contains(symptom.toLowerCase()))
                            .count();
                    return new DiseaseMatch(disease, matchCount);
                })
                .filter(dm -> dm.getMatchCount() > 0)
                .sorted((a, b) -> Long.compare(b.getMatchCount(), a.getMatchCount()))
                .map(DiseaseMatch::getDisease)
                .collect(Collectors.toList());
    }


    // ✅ NEW METHOD TO ADD DISEASE
    public Disease addDisease(Disease disease) {
        diseases.add(disease);
        return disease;
    }
    class DiseaseMatch {
        private Disease disease;
        private long matchCount;

        public DiseaseMatch(Disease disease, long matchCount) {
            this.disease = disease;
            this.matchCount = matchCount;
        }

        public Disease getDisease() {
            return disease;
        }

        public long getMatchCount() {
            return matchCount;
        }
    }

}
