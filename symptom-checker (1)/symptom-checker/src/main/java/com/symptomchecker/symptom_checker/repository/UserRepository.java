package com.symptomchecker.symptom_checker.repository;

import com.symptomchecker.symptom_checker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
}
