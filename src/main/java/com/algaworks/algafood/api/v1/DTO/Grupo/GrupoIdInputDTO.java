package com.algaworks.algafood.api.v1.DTO.Grupo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GrupoIdInputDTO {
    @NotNull
    private Long id;
}
