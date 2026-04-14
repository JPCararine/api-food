package com.algaworks.algafood.api.v1.DTO.Cidade;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CidadeInputDTO {
    @NotBlank
    private String nome;
    @Valid
    @NotNull
    private EstadoIdDTOPut estado;
}

