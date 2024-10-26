package com.dms.demo.Configuration;

import com.dms.demo.entity.AuthResponse;
import com.dms.demo.entity.User;
import com.dms.demo.userimplementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow CORS for frontend
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService; // Optional, if email sending is configured

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );

            // Generate JWT token using the updated JwtUtil
            final String jwt = jwtUtil.createToken(loginRequest.getUsername());

            // Load user details from the database
            User user = userService.authenticate(
                loginRequest.getUsername(), 
                loginRequest.getPassword(), // Removed the token parameter
                jwt // Pass the generated token to add it to active tokens
            );

            // Ensure user exists and return both the generated JWT token and user details
            return ResponseEntity.ok(new AuthResponse(jwt, user)); // Ensure AuthResponse is correctly defined

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            // Handle other exceptions, if necessary
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Encode password here only
        userService.registerNewUser(user); // Only save the user without re-encoding

        try {
            emailService.sendRegistrationEmail(user.getUsername());
        } catch (Exception e) {
            // Handle email sending failure
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

}
