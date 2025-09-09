package com.symptomchecker.symptom_checker.controller;

import com.symptomchecker.symptom_checker.model.User;
import com.symptomchecker.symptom_checker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> user = service.login(username, password);
        if (user.isPresent()) {
            return "✅ Login successful for " + username;
        } else {
            return "❌ Invalid username or password";
        }
    }
}
