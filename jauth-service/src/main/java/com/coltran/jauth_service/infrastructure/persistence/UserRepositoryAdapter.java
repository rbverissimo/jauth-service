package com.coltran.jauth_service.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.coltran.jauth_service.domain.model.AuthProvider;
import com.coltran.jauth_service.domain.model.User;
import com.coltran.jauth_service.domain.repository.UserRepository;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByAuthProviderAndProviderId(String authProvider, String providerId) {
        return jpaUserRepository.findByAuthProviderAndProviderId(
            AuthProvider.valueOf(authProvider), 
            providerId
        );
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }
    
    
}
