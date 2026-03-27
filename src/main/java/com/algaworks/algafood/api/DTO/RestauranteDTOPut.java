package com.algaworks.algafood.api.DTO;

import java.math.BigDecimal;

public class RestauranteDTOPut {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private boolean ativo;
    private boolean aberto;
    private CozinhaDTOPUT cozinhaDTOPUT;
}
