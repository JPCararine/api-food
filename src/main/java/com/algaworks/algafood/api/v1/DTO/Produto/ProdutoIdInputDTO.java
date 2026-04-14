package com.algaworks.algafood.api.v1.DTO.Produto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdutoIdInputDTO {
    @NotNull
    private Long id;
}
