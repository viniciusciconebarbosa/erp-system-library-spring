package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.LoginDTO;
import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.service.AuthService;
import com.biblioteca.erp_biblioteca.exception.EmailJaCadastradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("Deve registrar um novo usuário com sucesso")
    void deveRegistrarUsuarioComSucesso() throws Exception {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Teste da Silva");
        usuarioDTO.setEmail("teste@email.com");
        usuarioDTO.setSenha("senha123A");
        usuarioDTO.setIdade(25);

        Map<String, Object> response = new HashMap<>();
        response.put("token", "jwt-token");
        response.put("usuario", Map.of(
            "id", 1L,
            "nome", "Teste da Silva",
            "email", "teste@email.com"
        ));

        when(authService.registro(any(UsuarioDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.usuario.email").value("teste@email.com"));
    }

    @Test
    @DisplayName("Deve realizar login com sucesso")
    void deveRealizarLoginComSucesso() throws Exception {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("teste@email.com");
        loginDTO.setSenha("senha123A");

        Map<String, Object> response = new HashMap<>();
        response.put("token", "jwt-token");
        response.put("usuario", Map.of(
            "id", 1L,
            "email", "teste@email.com"
        ));

        when(authService.login(any(LoginDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.usuario.email").value("teste@email.com"));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar registrar usuário com dados inválidos")
    void deveRetornarErroDadosInvalidosRegistro() throws Exception {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Te");
        usuarioDTO.setEmail("email-invalido");
        usuarioDTO.setSenha("123");
        usuarioDTO.setIdade(15);

        // Act & Assert
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar fazer login com credenciais inválidas")
    void deveRetornarErroCredenciaisInvalidasLogin() throws Exception {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("email-invalido");
        loginDTO.setSenha("");

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar registrar com email já cadastrado")
    void deveRetornarErroEmailJaCadastrado() throws Exception {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Teste da Silva");
        usuarioDTO.setEmail("existente@email.com");
        usuarioDTO.setSenha("senha123A");
        usuarioDTO.setIdade(25);

        when(authService.registro(any(UsuarioDTO.class)))
            .thenThrow(new EmailJaCadastradoException());

        // Act & Assert
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar login com senha incorreta")
    void deveRetornarErroSenhaIncorreta() throws Exception {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("teste@email.com");
        loginDTO.setSenha("senhaErrada");

        when(authService.login(any(LoginDTO.class)))
            .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar registrar com senha fraca")
    void deveRetornarErroSenhaFraca() throws Exception {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Teste da Silva");
        usuarioDTO.setEmail("teste@email.com");
        usuarioDTO.setSenha("123"); // senha muito curta
        usuarioDTO.setIdade(25);

        // Act & Assert
        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar fazer requisição sem content-type")
    void deveRetornarErroSemContentType() throws Exception {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("teste@email.com");
        loginDTO.setSenha("senha123A");

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnsupportedMediaType());
    }
}