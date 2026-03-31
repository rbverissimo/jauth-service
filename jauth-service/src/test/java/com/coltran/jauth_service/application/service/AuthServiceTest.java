package com.coltran.jauth_service.application.service;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coltran.jauth_service.application.dto.RegisterRequest;
import com.coltran.jauth_service.application.dto.UserInfoResponse;
import com.coltran.jauth_service.domain.exception.UserNotFoundException;
import com.coltran.jauth_service.domain.model.Role;
import com.coltran.jauth_service.domain.model.RoleName;
import com.coltran.jauth_service.domain.model.User;
import com.coltran.jauth_service.domain.repository.RoleRepository;
import com.coltran.jauth_service.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Should successfully register a local user and return a response")
    void registerLocalUser_Should_Return_Success() {

        RegisterRequest request = new RegisterRequest("test@mail.com", "testUser123",  "password123");
        String encodedPassword = "encodedPassword123";

        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);

        User savedUser = User.createLocalUser(request.email(), request.publicName(), encodedPassword);
        savedUser.setRoles(Set.of(userRole));

        
        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn(encodedPassword);
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserInfoResponse response = authService.registerLocalUser(request);

        assertNotNull(response);
        assertEquals(request.email(), response.email());
        assertEquals(request.publicName(), response.name());

        verify(userRepository).existsByEmail(request.email());
        verify(passwordEncoder).encode(request.password());
        verify(roleRepository).findByName(RoleName.ROLE_USER);
        verify(userRepository).save(any(User.class));

    }

    @Test
    @DisplayName("Should throw Exception when registering local User with an already existing email ")
    void registerLocalUser_EmailAlreadyExists_ThrowsExeception(){

        RegisterRequest request = new RegisterRequest("test@mail.com", "testUser123", "password123");

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.registerLocalUser(request);
        });

        assertEquals("Error: Email already in use", exception.getMessage());

        verify(passwordEncoder, never()).encode(anyString());
        verify(roleRepository, never()).findByName(any(RoleName.class));
        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    @DisplayName("Should throw RuntimeException when default ROLE_USER is not found in database")
    void registerLocalUser_RoleNotFound_ThrowsException() {
       

        RegisterRequest request = new RegisterRequest(
            "test@mail.com", 
            "Test User", 
            "password123"
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.registerLocalUser(request);
        });

        assertEquals("Error: Default ROLE_USER not found", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    @DisplayName("Should successfully retrieve a user by email")
    void getUserByEmail_Success() {

        String email = "findme@mail.com";
        User expectedUser = User.createLocalUser(email, "Found User", "hashed");
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        User actualUser = authService.getUserByEmail(email);

        assertNotNull(actualUser);
        assertEquals(email, actualUser.getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when retrieving a non-existent email")
    void getUserByEmail_NotFound_ThrowsException() {
 
        String email = "missing@mail.com";
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            authService.getUserByEmail(email);
        });

        assertEquals("User not found with email: " + email, exception.getMessage());
        verify(userRepository).findByEmail(email);
    }
    
}
