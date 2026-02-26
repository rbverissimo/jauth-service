package com.coltran.jauth_service.infrastructure.security.jwt;


import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
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
    private static final long TEST_EXPIRATION_MS = 86400000L;

    @BeforeEach
    void setUp(){
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", TEST_EXPIRATION_MS);
        jwtTokenProvider.init();
    }

    @Test
    @DisplayName("Generate Token should include User ID, Email and Roles")
    void generateToken_shouldCreateValidSignedJwtToken() {
        User user = createTestUser();

        String token = jwtTokenProvider.generateToken(user);

        assertNotNull(token);

        SecretKey key = Keys.hmacShaKeyFor(TEST_SECRET.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        assertEquals(user.getId(), claims.getSubject(), "Subject should be the User's ID.");
        assertTrue(claims.getExpiration().after(new Date()), "Token expiration date should be valid.");
        assertEquals("test@mail.com", claims.get("email"), "Email claim should be valid.");
        assertEquals("ROLE_USER", claims.get("roles"), "Role claims must contain ROLE_USER");
    }

    @Test
    @DisplayName("Validate Token should return true for a valid token")
    void validateToken_shouldReturnTrueForValidToken() {
        User user = createTestUser();
        String token = jwtTokenProvider.generateToken(user);

        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("Validate Token should return false for an expired token")
    void validateToken_shouldReturnFalseForExpiredToken() {
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", -1000L); // Set expiration to the past
        User user = createTestUser();
        String token = jwtTokenProvider.generateToken(user);

        assertFalse(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("Validate Token should return false for a malformed token")
    void validateToken_shouldReturnFalseForMalformedToken() {
        assertFalse(jwtTokenProvider.validateToken("malformed.token.here"));
    }

    @Test
    @DisplayName("Validate Token should return false for token with different signature")
    void validateToken_shouldReturnFalseForTokenWithDifferentSignature() {
        String otherSecret = "other-secret-key-that-is-long-enough-for-hs512-validation";
        SecretKey otherKey = Keys.hmacShaKeyFor(otherSecret.getBytes(StandardCharsets.UTF_8));
        
        String token = Jwts.builder()
            .subject("user-id")
            .signWith(otherKey)
            .compact();

        assertFalse(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("Get User ID from token should return correct ID for valid token")
    void getUserIdFromToken_shouldReturnUserIdForValidToken() {
        User user = createTestUser();
        String token = jwtTokenProvider.generateToken(user);

        assertEquals(user.getId(), jwtTokenProvider.getUserIdFromToken(token));
    }

    @Test
    @DisplayName("Get User ID from token should throw BadCredentialsException for invalid token")
    void getUserIdFromToken_shouldThrowExceptionForInvalidToken() {
        assertThrows(BadCredentialsException.class, () -> 
            jwtTokenProvider.getUserIdFromToken("invalid-token")
        );
    }

    private User createTestUser() {
        User user = new User();
        user.setId("01HXTQQ7G6J0X0D0M1J6Y1T8K6");
        user.setEmail("test@mail.com");
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setRoles(Set.of(new Role(RoleName.ROLE_USER)));
        return user;
    }
    
}
