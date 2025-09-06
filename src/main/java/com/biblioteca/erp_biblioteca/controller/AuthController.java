package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.LoginDTO;
import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "API para autenticação e registro de usuários")
@Validated
public class AuthController {
    private final AuthService authService;

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
    public ResponseEntity<?> registro(
        @Parameter(description = "Dados do usuário para registro")
        @Validated(UsuarioDTO.Create.class) @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(authService.registro(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(
        summary = "Realiza login",
        description = "Autentica um usuário no sistema e retorna um token JWT"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<?> login(
        @Parameter(description = "Credenciais do usuário")
        @Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }
}