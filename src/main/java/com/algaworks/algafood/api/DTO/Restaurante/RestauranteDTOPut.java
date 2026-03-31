package com.algaworks.algafood.api.DTO.Restaurante;

import com.algaworks.algafood.api.DTO.Endereco.EnderecoInputDTO;
import com.algaworks.algafood.domain.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class RestauranteDTOPut {

    @NotBlank
    private String nome;
    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    private Boolean ativo;
    @Valid
    @NotNull
    private CozinhaIdDTOPUT cozinha;
    @NotNull
    @Valid
    private EnderecoInputDTO endereco;
}
