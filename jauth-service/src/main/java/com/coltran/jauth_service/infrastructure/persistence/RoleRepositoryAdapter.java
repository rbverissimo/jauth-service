package com.coltran.jauth_service.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.coltran.jauth_service.domain.model.Role;
import com.coltran.jauth_service.domain.repository.RoleRepository;

@Repository
public class RoleRepositoryAdapter implements RoleRepository {

    private JpaRoleRepository jpaRoleRepository;

    public RoleRepositoryAdapter(JpaRoleRepository jpaRoleRepository) {
        this.jpaRoleRepository = jpaRoleRepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRoleRepository.findByName(name);
    }

    
}
