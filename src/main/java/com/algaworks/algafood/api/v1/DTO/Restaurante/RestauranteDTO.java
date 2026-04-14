package com.algaworks.algafood.api.v1.DTO.Restaurante;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

@JsonPropertyOrder({"id", "nome", "frete",  "ativo", "aberto", "cozinha"})
@Data
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal frete;
    private CozinhaDTO cozinha;
    private Boolean ativo;
    private Boolean aberto;
}
