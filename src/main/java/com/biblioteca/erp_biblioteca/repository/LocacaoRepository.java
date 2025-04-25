package com.biblioteca.erp_biblioteca.repository;

import com.biblioteca.erp_biblioteca.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, UUID> {
    List<Locacao> findByUsuarioId(UUID usuarioId);
}
