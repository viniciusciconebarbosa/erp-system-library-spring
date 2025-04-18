package com.biblioteca.erp_biblioteca.model;

import com.biblioteca.erp_biblioteca.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String nome;
    private int idade;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "doador")
    private Set<Livro> livrosDoados;

    @OneToMany(mappedBy = "usuario")
    private Set<Locacao> locacoes;
    
    // Getters
    public UUID getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public Role getRole() {
        return role;
    }
    
    public Set<Livro> getLivrosDoados() {
        return livrosDoados;
    }
    
    public Set<Locacao> getLocacoes() {
        return locacoes;
    }
    
    // Setters
    public void setId(UUID id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public void setLivrosDoados(Set<Livro> livrosDoados) {
        this.livrosDoados = livrosDoados;
    }
    
    public void setLocacoes(Set<Locacao> locacoes) {
        this.locacoes = locacoes;
    }
}
