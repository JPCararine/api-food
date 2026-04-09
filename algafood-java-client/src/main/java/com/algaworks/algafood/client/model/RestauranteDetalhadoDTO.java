package com.algaworks.algafood.client.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class RestauranteDetalhadoDTO  {

    private Long id;
    private String nome;
    private BigDecimal frete;
    private CozinhaDTO cozinha;


}
