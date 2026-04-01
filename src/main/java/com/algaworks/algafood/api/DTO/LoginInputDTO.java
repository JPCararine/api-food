package com.algaworks.algafood.api.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginInputDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
}
