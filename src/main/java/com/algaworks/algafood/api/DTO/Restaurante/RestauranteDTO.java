package com.algaworks.algafood.api.DTO.Restaurante;


import com.algaworks.algafood.api.DTO.Endereco.EnderecoDTO;
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

}
