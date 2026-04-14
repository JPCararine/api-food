package com.algaworks.algafood.api.v1.DTO.FormaPagamento;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FormaPagamentoInputDTO {

    @NotBlank
    private String descricao;
}
