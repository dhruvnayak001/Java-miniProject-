package com.symptomchecker.symptom_checker.service;

import com.symptomchecker.symptom_checker.model.User;
import com.symptomchecker.symptom_checker.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
        this.encoder = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword())); // encrypt password
        return repo.save(user);
    }

    public Optional<User> login(String username, String rawPassword) {
        return repo.findByUsername(username)
                .filter(user -> encoder.matches(rawPassword, user.getPassword()));
    }
}
