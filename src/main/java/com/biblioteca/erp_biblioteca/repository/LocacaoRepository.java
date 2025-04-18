package com.biblioteca.erp_biblioteca.repository;

import com.biblioteca.erp_biblioteca.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, UUID> {
    Locacao save(Locacao locacao);
    Optional<Locacao> findById(UUID id);
    void deleteById(UUID id);
}
