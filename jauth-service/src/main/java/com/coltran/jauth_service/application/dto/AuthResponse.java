package com.coltran.jauth_service.application.dto;

public record AuthResponse(
    String userId,
    String token,
    String tokenType
) {}
