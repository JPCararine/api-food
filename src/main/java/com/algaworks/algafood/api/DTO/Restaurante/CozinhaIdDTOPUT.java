package com.algaworks.algafood.api.DTO.Restaurante;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CozinhaIdDTOPUT {

    @NotNull
    private Long id;



}
