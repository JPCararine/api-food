package com.algaworks.algafood.api.DTO.Endereco;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CidadeIdInputDTO {
    @NotNull
    private Long id;
}
