package com.biblioteca.erp_biblioteca.security;

import com.biblioteca.erp_biblioteca.enums.Role;
import com.biblioteca.erp_biblioteca.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Configurar valores usando ReflectionTestUtils
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", "minha-chave-secreta-super-segura-para-jwt-token");
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", 86400000); // 24 horas

        usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("João Silva")
                .email("joao@email.com")
                .role(Role.COMUM)
                .build();
    }

    @Test
    void deveGerarTokenComSucesso() {
        // Act
        String token = jwtTokenProvider.generateToken(usuario);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    void deveExtrairUserIdDoToken() {
        // Arrange
        String token = jwtTokenProvider.generateToken(usuario);

        // Act
        String userId = jwtTokenProvider.getUserIdFromToken(token);

        // Assert
        assertNotNull(userId);
        assertEquals(usuario.getId().toString(), userId);
    }

    @Test
    void deveValidarTokenValido() {
        // Arrange
        String token = jwtTokenProvider.generateToken(usuario);

        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void deveInvalidarTokenInvalido() {
        // Arrange
        String tokenInvalido = "token.invalido.qualquer";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(tokenInvalido);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void deveInvalidarTokenVazio() {
        // Arrange
        String tokenVazio = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            jwtTokenProvider.validateToken(tokenVazio);
        });
    }

    @Test
    void deveInvalidarTokenNulo() {
        // Arrange
        String tokenNulo = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            jwtTokenProvider.validateToken(tokenNulo);
        });
    }

    @Test
    void deveGerarTokensDiferentesParaUsuariosDiferentes() {
        // Arrange
        Usuario usuario2 = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("Maria Silva")
                .email("maria@email.com")
                .role(Role.ADMIN)
                .build();

        // Act
        String token1 = jwtTokenProvider.generateToken(usuario);
        String token2 = jwtTokenProvider.generateToken(usuario2);

        // Assert
        assertNotEquals(token1, token2);
    }

    @Test
    void deveGerarTokenComRoleCorreto() {
        // Arrange
        usuario.setRole(Role.ADMIN);

        // Act
        String token = jwtTokenProvider.generateToken(usuario);
        String userId = jwtTokenProvider.getUserIdFromToken(token);

        // Assert
        assertNotNull(token);
        assertEquals(usuario.getId().toString(), userId);
    }

    @Test
    void deveLancarExcecaoAoExtrairUserIdDeTokenInvalido() {
        // Arrange
        String tokenInvalido = "token.invalido.qualquer";

        // Act & Assert
        assertThrows(Exception.class, () -> {
            jwtTokenProvider.getUserIdFromToken(tokenInvalido);
        });
    }
}
