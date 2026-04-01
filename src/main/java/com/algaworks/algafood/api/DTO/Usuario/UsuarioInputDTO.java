package com.algaworks.algafood.api.DTO.Usuario;

import com.algaworks.algafood.api.DTO.Grupo.GrupoIdInputDTO;
import com.algaworks.algafood.api.DTO.Grupo.GrupoInputDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioInputDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
    @Valid
    @NotNull
    private List<GrupoIdInputDTO> grupos;
}
