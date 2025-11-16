package com.coltran.jauth_service.application.service;

import org.apache.kafka.common.config.types.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coltran.jauth_service.application.dto.RegisterRequest;
import com.coltran.jauth_service.domain.model.User;
import com.coltran.jauth_service.domain.repository.RoleRepository;
import com.coltran.jauth_service.domain.repository.UserRepository;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;

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

    public UserInfoResponse registerLocalUser(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new IllegalArgumentException("Error: Email already in use");
        }
        User user = new User(
            registerRequest.email(),
            registerRequest.publicName(),
            passwordEncoder.encode(registerRequest.password())
        );
        return null; 
    }


}
