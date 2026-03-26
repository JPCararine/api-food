package com.algaworks.algafoodapi2.DTO;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private boolean ativo;
    private boolean aberto;
    LocalDateTime dataCadastro = LocalDateTime.now();
    LocalDateTime dataAtualizacao = LocalDateTime.now();
    private CozinhaDTO cozinha;
}
