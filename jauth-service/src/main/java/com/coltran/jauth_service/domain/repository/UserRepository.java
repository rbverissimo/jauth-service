package com.coltran.jauth_service.domain.repository;

import java.util.Optional;

import com.coltran.jauth_service.domain.model.AuthProvider;
import com.coltran.jauth_service.domain.model.User;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    Optional<User> findByAuthProviderAndProviderId(AuthProvider authProvider, String providerId);
    Boolean existsByEmail(String email);
    User save(User user);

}
