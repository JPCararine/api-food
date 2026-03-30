package com.algaworks.algafood.api.DTO.Cozinha;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CozinhaInputDTO {


    @NotBlank
    private String nome;
}
