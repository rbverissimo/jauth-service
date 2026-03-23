package com.coltran.jauth_service.infrastructure.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.coltran.jauth_service.application.service.AuthService;
import com.coltran.jauth_service.domain.model.User;

public class CustomUserDetailsService implements UserDetailsService {

    private final AuthService authService;

    public CustomUserDetailsService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = authService.getUserByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );
    }

    
}
