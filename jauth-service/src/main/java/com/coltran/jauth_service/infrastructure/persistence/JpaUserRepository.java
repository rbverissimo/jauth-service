package com.coltran.jauth_service.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coltran.jauth_service.domain.model.AuthProvider;
import com.coltran.jauth_service.domain.model.User;

public interface JpaUserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);

    /**
     * @param authProvider
     * @param providerId
     * @return An Optional containing the user found based on those params
     */
    Optional<User> findByAuthProviderAndProviderId(AuthProvider authProvider, String providerId);

    Boolean existsByEmail(String email);
    
}
