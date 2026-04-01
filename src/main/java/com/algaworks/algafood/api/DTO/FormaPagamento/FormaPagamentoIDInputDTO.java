package com.algaworks.algafood.api.DTO.FormaPagamento;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormaPagamentoIDInputDTO {
    @NotNull
    private Long id;
}
