package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.LoginDTO;
import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.enums.Role;
import com.biblioteca.erp_biblioteca.exception.EmailJaCadastradoException;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import com.biblioteca.erp_biblioteca.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    
    public Map<String, Object> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getSenha()
            )
        );

        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String token = tokenProvider.generateToken(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", usuario);
        
        return response;
    }

    public Map<String, Object> registro(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new EmailJaCadastradoException();
        }

        Usuario usuario = Usuario.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(passwordEncoder.encode(usuarioDTO.getSenha()))
            .idade(usuarioDTO.getIdade())
            .role(Role.COMUM)
            .build();

        usuario = usuarioRepository.save(usuario);
        String token = tokenProvider.generateToken(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", usuario);
        
        return response;
    }
}