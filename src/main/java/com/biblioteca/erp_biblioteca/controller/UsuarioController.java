package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.DeleteResponse;
import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.enums.Role;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.ok(novoUsuario);
    }

    @PostMapping("/admin/registro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> registrarAdmin(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario novoAdmin = usuarioService.criarUsuarioAdmin(usuarioDTO);
        return ResponseEntity.ok(novoAdmin);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable UUID id,
            @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> atualizarRole(
            @PathVariable UUID id,
            @RequestParam Role novaRole) {
        Usuario usuarioAtualizado = usuarioService.atualizarRoleUsuario(id, novaRole);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteResponse> deletarUsuario(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        DeleteResponse response = new DeleteResponse(
            "Usuário deletado com sucesso",
            id.toString(),
            "Usuario"
        );
        return ResponseEntity.ok(response);
    }
}