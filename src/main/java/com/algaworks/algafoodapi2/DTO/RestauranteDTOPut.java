package com.algaworks.algafoodapi2.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RestauranteDTOPut {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private boolean ativo;
    private boolean aberto;
    private CozinhaDTOPUT cozinhaDTOPUT;
}
