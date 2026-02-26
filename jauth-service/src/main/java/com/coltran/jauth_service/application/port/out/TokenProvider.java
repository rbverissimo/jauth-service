package com.coltran.jauth_service.application.port.out;

import com.coltran.jauth_service.domain.model.User;

public interface TokenProvider {

    String generateToken(User user);
    boolean validateToken(String token);
    String getUserIdFromToken(String token);
}
