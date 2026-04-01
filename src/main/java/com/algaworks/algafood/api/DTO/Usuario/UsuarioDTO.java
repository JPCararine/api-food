package com.algaworks.algafood.api.DTO.Usuario;

import com.algaworks.algafood.api.DTO.Grupo.GrupoDTO;
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
