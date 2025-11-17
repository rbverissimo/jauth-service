package com.coltran.jauth_service.application.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coltran.jauth_service.application.dto.RegisterRequest;
import com.coltran.jauth_service.application.dto.UserInfoResponse;
import com.coltran.jauth_service.domain.model.Role;
import com.coltran.jauth_service.domain.model.RoleName;
import com.coltran.jauth_service.domain.model.User;
import com.coltran.jauth_service.domain.repository.RoleRepository;
import com.coltran.jauth_service.domain.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(PasswordEncoder passwordEncoder, 
            UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserInfoResponse registerLocalUser(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new IllegalArgumentException("Error: Email already in use");
        }
        User user = new User(
            registerRequest.email(),
            registerRequest.publicName(),
            passwordEncoder.encode(registerRequest.password())
        );

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER.name()).orElseThrow(
            () -> new RuntimeException("Error: Default ROLE_USER not found")
        );

        user.setRoles(Set.of(userRole));
        User savedUser = userRepository.save(user);
        return UserInfoResponse.from(savedUser);
    }


}
