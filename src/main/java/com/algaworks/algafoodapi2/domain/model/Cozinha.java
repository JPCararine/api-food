package com.algaworks.algafoodapi2.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@Data
public class Cozinha {
    @NotNull(groups = Groups.CozinhaId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;


    public Cozinha() {
    }
    @OneToMany(mappedBy = "cozinha")
    @JsonIgnore
    public List<Restaurante> restaurantes;
}


