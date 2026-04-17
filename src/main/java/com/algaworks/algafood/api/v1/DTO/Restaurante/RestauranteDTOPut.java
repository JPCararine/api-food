package com.algaworks.algafood.api.v1.DTO.Restaurante;

import com.algaworks.algafood.api.v1.DTO.Endereco.EnderecoInputDTO;
import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioIdInputDTO;
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


    @Valid
    @NotNull
    private CozinhaIdDTOPUT cozinha;
    @NotNull
    @Valid
    private EnderecoInputDTO endereco;



}
