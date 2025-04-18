package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}