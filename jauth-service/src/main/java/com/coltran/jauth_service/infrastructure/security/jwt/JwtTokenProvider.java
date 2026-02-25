package com.coltran.jauth_service.infrastructure.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coltran.jauth_service.application.port.out.TokenProvider;
import com.coltran.jauth_service.domain.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;
    

    public String generateToken(User user){
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Instant now = Instant.now();
        Instant expiryDate = now.plus(jwtExpirationMs, ChronoUnit.MILLIS);

        String roles = user.getRoles()
            .stream()
            .map(role -> role.getName().name())
            .collect(Collectors.joining(","));
        
        return Jwts.builder()
            .subject(user.getId())
            .claim("email", user.getEmail())
            .claim("roles", roles)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiryDate))
            .signWith(key)
            .compact();
    }
}
