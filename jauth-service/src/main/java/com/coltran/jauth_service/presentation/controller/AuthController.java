package com.coltran.jauth_service.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import com.coltran.jauth_service.domain.model.User;
import com.coltran.jauth_service.infrastructure.security.jwt.JwtTokenProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService; 
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    public AuthController(AuthService authService, 
        AuthenticationManager authenticationManager, 
        JwtTokenProvider tokenProvider
    ) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginLocalUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password()
            )
        );

        User user = authService.getUserByEmail(loginRequest.email());

        String jwt = tokenProvider.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(user.getId(), jwt, "Bearer"));

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
