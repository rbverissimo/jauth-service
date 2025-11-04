package com.coltran.jauth_service.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coltran.jauth_service.domain.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * 
     * @param name
     * @return An Optional of Role based on the name passed
     */
    Optional<Role> findByName(String name);
    
}
