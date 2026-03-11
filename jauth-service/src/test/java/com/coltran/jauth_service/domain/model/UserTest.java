package com.coltran.jauth_service.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
    

    @Test
    @DisplayName("Default constructor should generate valid ULID and initialize roles")
    void defaultConstructor_ShouldInitializeIdAndRoles() {

        User user = new User();

        assertNotNull(user.getId(), "ULID should be generated");
        assertEquals(26, user.getId().length(), "ULID string representation must be 26 characters long ");
        
        assertNotNull(user.getRoles(), "Roles should be initialized");
        assertEquals(0, user.getRoles().size(), "Roles should be empty");

        assertTrue(user.isNew(), "A newly instantiated entity should be marked as new");
        
    }

    @Test
    @DisplayName("Parametrized constructor should initialize fields correctly, including generating ULID and provided fields")
    void parametrizedConstructor_ShouldInitializeAllRequiredFields() {
        String email = "test@example.com";
        String name = "Test User";
        String password = "hashedPassword";

        User user = new User(email, name, password);

        assertNotNull(user.getId(), "ULID should be generated");
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getPublicName());
        assertEquals(password, user.getPassword());
        assertNotNull(user.getRoles(), "Roles should be initialized");
        assertTrue(user.getRoles().isEmpty());
        assertTrue(user.isNew());

    }

    void roleManagement_ShouldUpdateRolesSet() {
        User user = new User();

        Role roleUser = new Role(RoleName.ROLE_USER);
        Role roleAdmin = new Role(RoleName.ROLE_ADMIN);

        user.getRoles().add(roleUser);
        user.getRoles().add(roleAdmin);

        assertEquals(2, user.getRoles().size(), "User should have two roles");
        assertTrue(user.getRoles().contains(roleUser), "User should contain role USER");
        assertTrue(user.getRoles().contains(roleAdmin), "User should contain role ADMIN");


    }

    @Test
    @DisplayName("markNotNew should set isNew to false")
    void markNotNew_ShouldSetIsNewToFalse() {
        User user = new User();
        assertTrue(user.isNew());
        user.markNotNew();

        assertTrue(!user.isNew(), "isNew should be false after calling markNotNew");   

    }

    @Test
    @DisplayName("Should correctly set and retrieve state management fields (Timestamps and AuthProvider)")
    void stateFields_ShouldWorkCorrectly() {
        User user = new User();

    }  
}
 