package com.coltran.jauth_service.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.coltran.jauth_service.application.dto.AuthResponse;
import com.coltran.jauth_service.application.dto.LoginRequest;
import com.coltran.jauth_service.application.dto.RegisterRequest;
import com.coltran.jauth_service.application.dto.UserInfoResponse;
import com.coltran.jauth_service.application.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService; 

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginLocalUser(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.noContent().build(); 
    }

    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserInfoResponse userInfoResponse = authService.registerLocalUser(registerRequest);

         URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/api/users/{id}")
            .buildAndExpand(userInfoResponse.id()).toUri();

        return ResponseEntity.created(location).body(userInfoResponse);
    }
    
}
