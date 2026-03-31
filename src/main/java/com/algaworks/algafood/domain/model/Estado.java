package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Builder
public class Estado {
    @NotNull(groups = Groups.EstadoId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;


    public Estado() {
    }
    @ToString.Exclude
    @OneToMany(mappedBy = "estado")
    private List<Cidade> cidades;
}
