package com.algaworks.algafood.api.DTO.Cidade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoIdDTOPut {

    @Valid
    @NotNull
    private Long id;
}
