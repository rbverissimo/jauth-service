package com.coltran.jauth_service.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
 
    private static final long serialVersionUID = 1L;

    private String id;
    private String email;
    private String publicName;
    private String password;
    private Set<Role> roles;

    public User() {
        this.id = UlidCreator.getUlid().toString();
        this.roles = new HashSet<>();
    }


}
