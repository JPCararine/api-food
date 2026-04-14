package com.algaworks.algafood.api.v1.DTO.Senha;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SenhaInput {
    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String senhaNova;
}
