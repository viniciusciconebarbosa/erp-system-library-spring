package com.biblioteca.erp_biblioteca.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("Deve conter todas as roles esperadas")
    void deveConterTodasAsRolesEsperadas() {
        // Arrange & Act
        Role[] roles = Role.values();

        // Assert
        assertEquals(2, roles.length);
        assertTrue(containsRole(roles, "ADMIN"));
        assertTrue(containsRole(roles, "COMUM"));
    }

    @Test
    @DisplayName("Deve retornar role por nome")
    void deveRetornarRolePorNome() {
        // Arrange & Act
        Role admin = Role.valueOf("ADMIN");
        Role comum = Role.valueOf("COMUM");

        // Assert
        assertEquals(Role.ADMIN, admin);
        assertEquals(Role.COMUM, comum);
    }

    @Test
    @DisplayName("Deve lançar exceção para role inválida")
    void deveLancarExcecaoParaRoleInvalida() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Role.valueOf("ROLE_INEXISTENTE");
        });
    }

    private boolean containsRole(Role[] roles, String nome) {
        for (Role role : roles) {
            if (role.name().equals(nome)) {
                return true;
            }
        }
        return false;
    }
}
