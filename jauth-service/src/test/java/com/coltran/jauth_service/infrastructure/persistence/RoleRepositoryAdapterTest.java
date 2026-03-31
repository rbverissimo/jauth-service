package com.coltran.jauth_service.infrastructure.persistence;

import com.coltran.jauth_service.domain.model.Role;
import com.github.f4b6a3.ulid.UlidCreator;
import com.coltran.jauth_service.domain.model.RoleName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(RoleRepositoryAdapter.class)
public class RoleRepositoryAdapterTest {

    @Autowired
    private RoleRepositoryAdapter roleRepositoryAdapter;


    @Test
    @DisplayName("Should successfully find a role by its name")
    void findByName_Success() {
        // Given
        Role role = new Role(RoleName.ROLE_USER);
        role.setId(UlidCreator.getUlid().toString());
        roleRepositoryAdapter.save(role);

        // When
        Optional<Role> foundRole = roleRepositoryAdapter.findByName(RoleName.ROLE_USER);

        // Then
        assertTrue(foundRole.isPresent(), "Role should be found");
        assertEquals(RoleName.ROLE_USER, foundRole.get().getName());
    }

    


    @Test
    @DisplayName("Should return empty Optional when role name does not exist")
    void testFindByName_WhenRoleDoesNotExist_ShouldReturnEmpty() {

        RoleName nonExistentRoleName = null;

        // When
        Optional<Role> result = roleRepositoryAdapter.findByName(nonExistentRoleName);

        // Then
        assertTrue(result.isEmpty(), "Should return empty when role does not exist");
    }
}
