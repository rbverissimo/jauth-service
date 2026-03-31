package com.coltran.jauth_service.domain.repository;

import java.util.Optional;

import com.coltran.jauth_service.domain.model.Role;
import com.coltran.jauth_service.domain.model.RoleName;

public interface RoleRepository {
    Optional<Role> findByName(RoleName name);
    Role save(Role role);
}
