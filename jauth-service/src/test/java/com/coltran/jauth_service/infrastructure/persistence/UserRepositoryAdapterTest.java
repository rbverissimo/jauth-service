package com.coltran.jauth_service.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.coltran.jauth_service.domain.model.AuthProvider;
import com.coltran.jauth_service.domain.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserRepositoryAdapter.class)
public class UserRepositoryAdapterTest {


    @Autowired
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    @DisplayName("Should save and successfully find a user by their email")
    void saveAndFindByEmail_Success() {
        
        String email = "test@hexagonal.com";
        User user = User.createLocalUser(email, "Hex User", "encodedPass");
        userRepositoryAdapter.save(user);

        Optional<User> foundUser = userRepositoryAdapter.findByEmail(email);

        assertTrue(foundUser.isPresent(), "User should be found in the database");
        assertEquals(email, foundUser.get().getEmail());
        assertEquals("Hex User", foundUser.get().getPublicName());
    }

    @Test
    @DisplayName("Should return empty Optional when email does not exist")
    void findByEmail_NotFound() {

        Optional<User> foundUser = userRepositoryAdapter.findByEmail("nobody@home.com");
        assertTrue(foundUser.isEmpty(), "Should return empty when user does not exist");
    }

    @Test
    @DisplayName("Should return true if email exists, false otherwise")
    void existsByEmail_WorksCorrectly() {

        String email = "exists@mail.com";
        
        User user = User.createLocalUser(email, "Exist User", "pass");
        userRepositoryAdapter.save(user);

        assertTrue(userRepositoryAdapter.existsByEmail(email), "Should return true for existing email");
        assertFalse(userRepositoryAdapter.existsByEmail("missing@mail.com"), "Should return false for non-existing email");
    }

    @Test
    @DisplayName("Should find a user by AuthProvider and Provider ID")
    void findByAuthProviderAndProviderId_Success() {
        
        String email = "oauth@mail.com";
        String providerId = "google-123456";
        User user = User.createOAuth2User(email, "OAuth User", AuthProvider.GOOGLE, providerId);
        
        userRepositoryAdapter.save(user);

        Optional<User> foundUser = userRepositoryAdapter.findByAuthProviderAndProviderId(AuthProvider.GOOGLE, providerId);
        
        assertTrue(foundUser.isPresent(), "OAuth2 User should be found");
        assertEquals(email, foundUser.get().getEmail());
        assertEquals(AuthProvider.GOOGLE, foundUser.get().getAuthProvider());
        assertEquals(providerId, foundUser.get().getProviderId());
    }
    
}
