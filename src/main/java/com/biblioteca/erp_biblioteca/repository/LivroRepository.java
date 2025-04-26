package com.biblioteca.erp_biblioteca.repository;

import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.dto.LivroResumoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
    
    @Query("SELECT new com.biblioteca.erp_biblioteca.dto.LivroResumoDTO(l.id, l.titulo, l.autor, l.capaFoto, l.disponivelLocacao) FROM Livro l")
    List<LivroResumoDTO> findAllResumido();
    
    @Query("SELECT new com.biblioteca.erp_biblioteca.dto.LivroResumoDTO(l.id, l.titulo, l.autor, l.capaFoto, l.disponivelLocacao) FROM Livro l WHERE l.disponivelLocacao = true")
    List<LivroResumoDTO> findDisponiveisResumido();
}
