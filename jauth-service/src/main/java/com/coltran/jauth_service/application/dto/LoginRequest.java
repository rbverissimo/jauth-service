package com.coltran.jauth_service.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email @NotBlank(message = "Email required") String email,
    @NotBlank(message = "Password required") String password
) { }