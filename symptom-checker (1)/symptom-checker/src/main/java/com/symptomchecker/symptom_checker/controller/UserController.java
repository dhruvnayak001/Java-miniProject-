package com.symptomchecker.symptom_checker.controller;

import com.symptomchecker.symptom_checker.model.User;
import com.symptomchecker.symptom_checker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (userService.usernameExists(user.getUsername())) {
            return "Username already exists!";
        }
        if (userService.emailExists(user.getEmail())) {
            return "Email already exists!";
        }
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User loginRequest) {
        User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            return "Login successful! Welcome " + user.getUsername();
        }
        return "Invalid email or password!";
    }
}
