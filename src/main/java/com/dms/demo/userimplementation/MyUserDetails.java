package com.dms.demo.userimplementation;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.dms.demo.entity.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> (GrantedAuthority) () -> role)
                .collect(Collectors.toList()); // Use collect for better readability
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // Other required methods
    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement logic if necessary
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement logic if necessary
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement logic if necessary
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement logic if necessary
    }
}
