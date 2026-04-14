package com.algaworks.algafood.api.v1.DTO.Usuario;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioIdInputDTO {
    @NotNull
    private Long id;

}
