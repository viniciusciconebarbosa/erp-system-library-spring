package com.biblioteca.erp_biblioteca.repository;

import com.biblioteca.erp_biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
    Livro save(Livro livro);
    Optional<Livro> findById(UUID id);
    void deleteById(UUID id);
}
