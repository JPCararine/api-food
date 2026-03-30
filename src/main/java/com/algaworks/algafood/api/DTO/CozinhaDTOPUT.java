package com.algaworks.algafood.api.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CozinhaDTOPUT {

    @NotNull
    private Long id;



}
