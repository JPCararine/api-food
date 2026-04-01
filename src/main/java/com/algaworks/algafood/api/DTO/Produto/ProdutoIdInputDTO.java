package com.algaworks.algafood.api.DTO.Produto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdutoIdInputDTO {
    @NotNull
    private Long id;
}
