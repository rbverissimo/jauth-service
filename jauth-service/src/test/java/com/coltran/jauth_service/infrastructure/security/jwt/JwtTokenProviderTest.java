package com.coltran.jauth_service.infrastructure.security.jwt;


import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.coltran.jauth_service.domain.model.AuthProvider;
import com.coltran.jauth_service.domain.model.Role;
import com.coltran.jauth_service.domain.model.RoleName;
import com.coltran.jauth_service.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class JwtTokenProviderTest {


    private JwtTokenProvider jwtTokenProvider;

    private static final String TEST_SECRET = "1234567890123456789012345678901212345678901234567890123456789012";
    private static final long TEST_EXPIRATION_MS = 6000L;

    @BeforeEach
    void setUp(){
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", TEST_EXPIRATION_MS);
    }

    @Test
    @DisplayName("Generate Token should include User ID, Email and Roles")
    void generateToken_shouldCreateValidSignedJwtToken() {
        User user = new User();
        user.setId("01HXTQQ7G6J0X0D0M1J6Y1T8K6");
        user.setEmail("test@mail.com");
        user.setAuthProvider(AuthProvider.LOCAL);

        Role userRole = new Role(RoleName.ROLE_USER);
        user.setRoles(Set.of(userRole));

        String token = jwtTokenProvider.generateToken(user);

        assertNotNull(token);
        System.out.println("Generated test token: " + token);

        SecretKey key = Keys.hmacShaKeyFor(TEST_SECRET.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        assertEquals(user.getId(), claims.getSubject(), "Subject should be the User's ID.");
        assertTrue(claims.getExpiration().after(new Date()), "Token expiration date should be valid.");

        assertEquals("test@mail.com", claims.get("email"), "Email claim should be valid.");

        String roles = claims.get("roles", String.class);
        assertNotNull(roles);
        assertTrue(roles.contains(RoleName.ROLE_USER.name()), "Role claims must contain ROLE_USER");

    }
    
}
