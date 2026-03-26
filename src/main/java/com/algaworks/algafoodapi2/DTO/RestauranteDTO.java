package com.algaworks.algafoodapi2.DTO;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@JsonPropertyOrder({"id", "nome", "taxaFrete",  "ativo", "aberto", "cozinha"})
@Data
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private boolean ativo;
    private boolean aberto;
    private CozinhaDTO cozinha;
}
