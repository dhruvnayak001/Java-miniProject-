package com.symptomchecker.symptom_checker.controller;

import com.symptomchecker.symptom_checker.model.User;
import com.symptomchecker.symptom_checker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*") // Use wildcard, but NO credentials/cookies/session!
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
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> userOpt = service.login(username, password);
        Map<String, Object> resp = new HashMap<>();
        if (userOpt.isPresent()) {
            resp.put("success", true);
            resp.put("user", userOpt.get());
            resp.put("message", "✅ Login successful for " + username);
        } else {
            resp.put("success", false);
            resp.put("message", "❌ Invalid username or password");
        }
        return resp;
    }
}