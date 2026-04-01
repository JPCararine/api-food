package com.algaworks.algafood.api.DTO.Grupo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GrupoIdInputDTO {
    @NotNull
    private Long id;
}
