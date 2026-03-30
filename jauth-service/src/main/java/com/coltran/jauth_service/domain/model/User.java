package com.coltran.jauth_service.domain.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;;

@Entity
@Table(name = "users")
public class User implements Serializable, Persistable<String> {
 
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 26)
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "public_name", nullable = false)
    private String publicName;

    @Column(name = "password", nullable = true)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthProvider authProvider;

    @Column(name = "provider_id", nullable = true)
    private String providerId;


    @Column(name = "verified_at", nullable = true)
    private OffsetDateTime verifiedAt;

    @SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
    @SQLRestriction("deleted_at IS NULL")
    @Column(name = "deleted_at", nullable = true)
    private OffsetDateTime deletedAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Transient
    private boolean isNew = true;

    public User() {
        this.id = UlidCreator.getUlid().toString();
        this.roles = new HashSet<>();
    }

    public User(String email, String name, String password) {
        
        this.id = UlidCreator.getUlid().toString();
        this.email = email;
        this.publicName = name;
        this.password = password;
        this.roles = new HashSet<>();
    }

    private User(String email, String name, String password, AuthProvider authProvider, String providerId) {
        
        this.id = UlidCreator.getUlid().toString();
        this.email = email;
        this.publicName = name;
        this.password = password;
        this.authProvider = authProvider;
        this.providerId = providerId;
        this.roles = new HashSet<>();
    }

    public static User createLocalUser(String email, String publicName, String encodedPassword) {
        return new User(
            email,
            publicName,
            encodedPassword,
            AuthProvider.LOCAL,
            null
        );
    }

    public static User createOAuth2User(String email, String publicName, AuthProvider authProvider, String providerId) {
        User auth2User =  new User(
            email,
            publicName,
            null,
            authProvider,
            providerId
        );
        auth2User.setVerifiedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return auth2User;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public OffsetDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(OffsetDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public OffsetDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(OffsetDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    } 

}
