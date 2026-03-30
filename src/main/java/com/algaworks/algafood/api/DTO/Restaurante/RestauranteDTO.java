package com.algaworks.algafood.api.DTO.Restaurante;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

@JsonPropertyOrder({"id", "nome", "taxaFrete",  "ativo", "aberto", "cozinha"})
@Data
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal frete;
    private boolean ativo;
    private boolean aberto;
    private CozinhaDTO cozinha;
}
