package com.coltran.jauth_service.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    
    @Email @NotBlank(message = "Email is required") 
    String email,
    
    @NotBlank(message = "Name is required") 
    String publicName,

    @NotBlank(message = "Password is required") 
    @Size(min = 8, message = "Password must be at least 8 characters long") 
    String password
){}