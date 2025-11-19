package com.coltran.jauth_service.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserInfoResponse userInfoResponse = authService.registerLocalUser(registerRequest);

        /* URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/api/users/{id}")
            .buildAndExpand(userInfoResponse.id()).toUri(); */
        
        URI location = null;

        return ResponseEntity.created(location).body(userInfoResponse);
    }
    
}
