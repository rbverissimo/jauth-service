package com.coltran.jauth_service.application.service;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        when(roleRepository.findByName(RoleName.ROLE_USER.name())).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserInfoResponse response = authService.registerLocalUser(request);

        assertNotNull(response);
        assertEquals(request.email(), response.email());
        assertEquals(request.publicName(), response.name());

        verify(userRepository).existsByEmail(request.email());
        verify(passwordEncoder).encode(request.password());
        verify(roleRepository).findByName(RoleName.ROLE_USER.name());
        verify(userRepository).save(any(User.class));

    }
    
}
