package com.dms.demo.entity;



import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.persistence.*;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String dob;
    private String address;
    private String mobile;
    

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;  // List of roles for the user
    
    @ElementCollection
    private List<String> activeTokens;

    private String registrationId; // Unique registration ID

    // Default constructor
    public User() {
        this.registrationId = generateRegistrationId(24); // Generate ID on creation
    }

    // Method to generate a random registration ID of specified length
    private String generateRegistrationId(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder registrationIdBuilder = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            registrationIdBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return registrationIdBuilder.toString();
    }

    // Parameterized constructor
    public User(Long id, String username, String password, List<String> roles,
                String firstname, String lastname, String dob, String address, String mobile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.address = address;
        this.mobile = mobile;
        this.registrationId = generateRegistrationId(24); // Generate ID
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role)) // Using lambda expression
                .collect(Collectors.toList()); // Collect to a List
    }
    
    public List<String> getActiveTokens() {
        return activeTokens;
    }

    public void setActiveTokens(List<String> activeTokens) {
        this.activeTokens = activeTokens;
    }
}
