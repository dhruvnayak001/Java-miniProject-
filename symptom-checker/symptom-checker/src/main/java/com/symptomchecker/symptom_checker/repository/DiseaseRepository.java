package com.symptomchecker.symptom_checker.repository;

import com.symptomchecker.symptom_checker.model.Disease;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends MongoRepository<Disease, String> {
    List<Disease> findBySymptomsContainingIgnoreCase(String symptom);
}
