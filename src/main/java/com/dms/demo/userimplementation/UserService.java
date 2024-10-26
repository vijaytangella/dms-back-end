package com.dms.demo.userimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.dms.demo.Userrepositary.UserRepository;
import com.dms.demo.entity.User;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerNewUser(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singletonList("ROLE_USER")); // Default role
        }
        // Remove the password encoding here
        return userRepository.save(user); // Save user
    }


    public User authenticate(String username, String password, String token) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Add the new token to the list of active tokens
            if (user.getActiveTokens() == null) {
                user.setActiveTokens(new ArrayList<>());
            }
            user.getActiveTokens().add(token);
            userRepository.save(user);
            return user;
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public void invalidateToken(String username, String token) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getActiveTokens() != null) {
            user.getActiveTokens().remove(token); // Remove the token from the list
            userRepository.save(user);
        }
    }

    public boolean isTokenValid(String username, String token) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getActiveTokens() != null && user.getActiveTokens().contains(token);
    }

}
