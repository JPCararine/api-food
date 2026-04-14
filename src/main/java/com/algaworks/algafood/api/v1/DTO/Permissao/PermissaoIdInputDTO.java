package com.algaworks.algafood.api.v1.DTO.Permissao;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissaoIdInputDTO {
    @NotNull
    private Long id;
}
