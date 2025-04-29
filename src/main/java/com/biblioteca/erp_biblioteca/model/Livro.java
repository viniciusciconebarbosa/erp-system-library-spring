package com.biblioteca.erp_biblioteca.model;

import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;
    
    private String autor;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private String capaFoto;
    private boolean disponivelLocacao;

    @Column(length = 2000)
    private String sinopse;

    @Enumerated(EnumType.STRING)
    private ClassificacaoEtaria classificacaoEtaria;

    @Enumerated(EnumType.STRING)
    private EstadoConservacao estadoConservacao;

    @ManyToOne
    @JoinColumn(name = "doador_id")
    private Usuario doador;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private Set<Locacao> locacoes;
}
