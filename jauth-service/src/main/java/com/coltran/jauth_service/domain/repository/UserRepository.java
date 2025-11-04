package com.coltran.jauth_service.domain.repository;

import java.security.AuthProvider;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coltran.jauth_service.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);

    /**
     * @param authProvider
     * @param providerId
     * @return An Optional containing the user found based on those params
     */
    Optional<User> findByAuthProviderAndProviderId(AuthProvider authProvider, String providerId);

    Boolean existsByEmail(String email);
    
}
