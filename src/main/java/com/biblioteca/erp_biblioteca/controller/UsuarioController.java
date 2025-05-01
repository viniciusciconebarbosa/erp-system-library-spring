package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.DeleteResponse;
import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.enums.Role;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "API para gerenciamento de usuários da biblioteca")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    @Operation(
        summary = "Registra um novo usuário",
        description = "Permite o registro de um novo usuário com perfil COMUM no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    public ResponseEntity<Usuario> registrarUsuario(
        @Parameter(description = "Dados do usuário para registro")
        @Validated(UsuarioDTO.Create.class) @RequestBody UsuarioDTO usuarioDTO) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.ok(novoUsuario);
    }

    @PostMapping("/admin/registro")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Registra um novo administrador",
        description = "Permite o registro de um novo usuário com perfil ADMIN no sistema. Requer permissão de administrador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão de administrador"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    public ResponseEntity<Usuario> registrarAdmin(
        @Parameter(description = "Dados do administrador para registro")
        @Validated(UsuarioDTO.Create.class) @RequestBody UsuarioDTO usuarioDTO) {
        Usuario novoAdmin = usuarioService.criarUsuarioAdmin(usuarioDTO);
        return ResponseEntity.ok(novoAdmin);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    @Operation(
        summary = "Busca um usuário específico",
        description = "Retorna os detalhes de um usuário específico baseado em seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão adequada"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> buscarUsuario(
        @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Lista todos os usuários",
        description = "Retorna uma lista com todos os usuários cadastrados no sistema. Apenas administradores têm acesso"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN")
    })
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Atualiza um usuário",
        description = "Permite a atualização dos dados de um usuário existente. A senha é opcional na atualização."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> atualizarUsuario(
        @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id,
        @Parameter(description = "Dados atualizados do usuário")
        @Validated(UsuarioDTO.Update.class) @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Atualiza o perfil de acesso do usuário",
        description = "Permite alterar o perfil de acesso (ADMIN/COMUM) de um usuário existente. Apenas administradores podem realizar esta operação"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil do usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Perfil inválido"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> atualizarRole(
        @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id,
        @Parameter(description = "Novo perfil do usuário (ADMIN ou COMUM)", example = "ADMIN")
        @RequestParam Role novaRole) {
        Usuario usuarioAtualizado = usuarioService.atualizarRoleUsuario(id, novaRole);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Remove um usuário",
        description = "Permite a remoção de um usuário do sistema. Apenas administradores podem realizar esta operação"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão de administrador"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<DeleteResponse> deletarUsuario(
        @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        DeleteResponse response = new DeleteResponse(
            "Usuário deletado com sucesso",
            id.toString(),
            "Usuario"
        );
        return ResponseEntity.ok(response);
    }
}