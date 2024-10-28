package com.example.demo.Configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Configuration.JwtUtil;
import com.example.demo.Service.Userservice;
import com.example.demo.entity.AuthResponse;
import com.example.demo.entity.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private Userservice userService; // Service to handle user logic
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder; // For encoding passwords

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    private JwtUtil jwtUtil; // Utility class for generating JWT tokens

    // Method to handle user registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); // Default to enabled
        user.setRole("USER"); // Set default role (could also be from the request)

        User savedUser = userService.saveUser(user); // Save user using service layer

        try {
            emailService.sendRegistrationEmail(user.getUsername());
        } catch (Exception e) {
            // Handle email sending failure
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    // Method to handle user login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the provided password matches the stored password
        if (userService.matchesPassword(user.getPassword(), existingUser.getPassword())) {
            // Generate JWT token
            String jwt = jwtUtil.generateToken(existingUser.getUsername());
            // Return JWT and user details in the response
            return ResponseEntity.ok(new AuthResponse(jwt, existingUser)); // Pass existingUser
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}