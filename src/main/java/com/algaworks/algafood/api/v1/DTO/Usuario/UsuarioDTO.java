package com.algaworks.algafood.api.v1.DTO.Usuario;

import com.algaworks.algafood.api.v1.DTO.Grupo.GrupoDTO;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;

    private List<GrupoDTO> grupos;
}
