package com.coltran.jauth_service.application.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.coltran.jauth_service.domain.model.User;

/**
 * The purpose of this record is to translate the User model into a number of values that materializes the User.
 */
public record UserInfoResponse(
    String id,
    String email,
    String name,
    String authProviderName,
    Set<String> roles
) {

    public static UserInfoResponse from(User user) {
        Set<String> roles = user.getRoles()
            .stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toSet());
            
        return new UserInfoResponse(
            user.getId(),
            user.getEmail(),
            user.getPublicName(),
            user.getAuthProvider().name(),
            roles
        );

    }
    
}
