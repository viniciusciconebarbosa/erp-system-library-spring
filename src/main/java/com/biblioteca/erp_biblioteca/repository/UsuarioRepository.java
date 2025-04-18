package com.biblioteca.erp_biblioteca.repository;

import com.biblioteca.erp_biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario registrarUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuario(UUID id);
}
