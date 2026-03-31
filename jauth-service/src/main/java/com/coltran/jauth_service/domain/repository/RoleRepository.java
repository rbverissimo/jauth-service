package com.coltran.jauth_service.domain.repository;

import java.util.Optional;

import com.coltran.jauth_service.domain.model.Role;

public interface RoleRepository {
    Optional<Role> findByName(String name);
}
