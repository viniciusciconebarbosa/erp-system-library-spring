package com.biblioteca.erp_biblioteca.model;

import com.biblioteca.erp_biblioteca.enums.StatusLocacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locacoes")
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLocacao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDevolucao;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private StatusLocacao status = StatusLocacao.ATIVA;
}
