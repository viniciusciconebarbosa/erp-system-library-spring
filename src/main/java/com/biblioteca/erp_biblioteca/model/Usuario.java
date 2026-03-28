package com.biblioteca.erp_biblioteca.model;

import com.biblioteca.erp_biblioteca.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID") // Adicione isso!
    private UUID id;
    
    private String nome;
    
    @Column(unique = true)
    private String email;
    
    private String senha;

    private int idade;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "doador", cascade = CascadeType.REMOVE)
    private Set<Livro> livrosDoados;

    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private Set<Locacao> locacoes;
}
