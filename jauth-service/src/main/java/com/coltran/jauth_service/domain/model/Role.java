package com.coltran.jauth_service.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 26)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private RoleName name; 

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return name != null ? name.equals(role.name) : Objects.equals(id, role.id);  
    }

    @Override
    public int hashCode() {
        return name != null ? Objects.hash(name) : getClass().hashCode();
        
    }

}
