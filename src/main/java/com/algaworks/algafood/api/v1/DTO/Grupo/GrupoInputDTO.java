package com.algaworks.algafood.api.v1.DTO.Grupo;

import com.algaworks.algafood.api.v1.DTO.Permissao.PermissaoIdInputDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GrupoInputDTO {

    @NotBlank
    private String nome;
    @Valid
    @NotNull
    private List<PermissaoIdInputDTO> permissoes;


}
