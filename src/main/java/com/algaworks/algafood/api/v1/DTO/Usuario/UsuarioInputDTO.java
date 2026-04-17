package com.algaworks.algafood.api.v1.DTO.Usuario;

import com.algaworks.algafood.api.v1.DTO.Grupo.GrupoIdInputDTO;
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

}
