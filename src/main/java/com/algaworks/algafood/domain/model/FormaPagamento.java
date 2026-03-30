package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Builder
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    public FormaPagamento() {
    }
    @ManyToMany
    public List<Restaurante> restaurantes;
}
