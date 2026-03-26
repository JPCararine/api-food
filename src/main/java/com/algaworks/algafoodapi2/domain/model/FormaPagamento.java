package com.algaworks.algafoodapi2.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    public List<Restaurante> restaurantes;
}
