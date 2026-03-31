package com.algaworks.algafood.api.DTO.FormaPagamento;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FormaPagamentoInputDTO {

    @NotBlank
    private String descricao;
}
