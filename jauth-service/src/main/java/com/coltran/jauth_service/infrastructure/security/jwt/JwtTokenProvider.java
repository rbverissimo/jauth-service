package com.coltran.jauth_service.infrastructure.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coltran.jauth_service.application.port.out.TokenProvider;
import com.coltran.jauth_service.domain.model.User;

@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;
    

    public String generateToken(User user){
        return null;
    }
}
