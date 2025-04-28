package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.enums.Role;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    private UsuarioDTO usuarioDTO;
    private Usuario usuario;
    private final UUID usuarioId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Test User");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setSenha("password123");
        usuarioDTO.setIdade(25);

        usuario = Usuario.builder()
                .id(usuarioId)
                .nome("Test User")
                .email("test@example.com")
                .senha("encodedPassword")
                .idade(25)
                .role(Role.COMUM)
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveRegistrarAdminComSucesso() throws Exception {
        when(usuarioService.criarUsuarioAdmin(any(UsuarioDTO.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios/admin/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioId.toString()))
                .andExpect(jsonPath("$.nome").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "COMUM")
    void naoDevePermitirRegistroDeAdminPorUsuarioComum() throws Exception {
        mockMvc.perform(post("/api/usuarios/admin/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveBuscarUsuarioComSucesso() throws Exception {
        when(usuarioService.buscarUsuario(usuarioId)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/" + usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioId.toString()))
                .andExpect(jsonPath("$.nome").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void naoDeveBuscarUsuarioSemAutenticacao() throws Exception {
        mockMvc.perform(get("/api/usuarios/" + usuarioId))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message").value("Acesso não autorizado. Por favor, faça login."))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveListarTodosUsuariosComSucesso() throws Exception {
        Usuario outroUsuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("Other User")
                .email("other@example.com")
                .senha("encodedPassword")
                .idade(30)
                .role(Role.COMUM)
                .build();

        when(usuarioService.listarTodos()).thenReturn(Arrays.asList(usuario, outroUsuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(usuarioId.toString()))
                .andExpect(jsonPath("$[0].nome").value("Test User"))
                .andExpect(jsonPath("$[1].nome").value("Other User"));
    }

    @Test
    @WithMockUser(roles = "COMUM")
    void naoDevePermitirListagemDeUsuariosPorUsuarioComum() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveAtualizarUsuarioComSucesso() throws Exception {
        UsuarioDTO atualizacaoDTO = new UsuarioDTO();
        atualizacaoDTO.setNome("Updated Name");
        atualizacaoDTO.setEmail("updated@example.com");
        atualizacaoDTO.setIdade(26);

        Usuario usuarioAtualizado = Usuario.builder()
                .id(usuarioId)
                .nome("Updated Name")
                .email("updated@example.com")
                .senha("encodedPassword")
                .idade(26)
                .role(Role.COMUM)
                .build();

        when(usuarioService.atualizarUsuario(any(UUID.class), any(UsuarioDTO.class)))
                .thenReturn(usuarioAtualizado);

        mockMvc.perform(put("/api/usuarios/" + usuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atualizacaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.idade").value(26));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveDeletarUsuarioComSucesso() throws Exception {
        mockMvc.perform(delete("/api/usuarios/" + usuarioId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "COMUM")
    void naoDevePermitirDelecaoDeUsuarioPorUsuarioComum() throws Exception {
        mockMvc.perform(delete("/api/usuarios/" + usuarioId))
                .andExpect(status().isForbidden());
    }
} 