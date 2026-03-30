package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Permissao {

    public Permissao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    @ManyToMany
    private List<Grupo> grupos;
}
