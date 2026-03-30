package com.algaworks.algafood.api.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class RestauranteDTOPut {

    private Long id;
    @NotBlank
    private String nome;
    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaDTOPUT cozinha;
}
