package com.coltran.jauth_service.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coltran.jauth_service.domain.model.Role;
import com.coltran.jauth_service.domain.model.RoleName;

public interface JpaRoleRepository extends JpaRepository<Role, String>{

    /**
     * 
     * @param name
     * @return An Optional of Role based on the name passed
     */
    Optional<Role> findByName(RoleName name);
    
} 