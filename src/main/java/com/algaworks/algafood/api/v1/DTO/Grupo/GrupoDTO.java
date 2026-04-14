package com.algaworks.algafood.api.v1.DTO.Grupo;

import com.algaworks.algafood.api.v1.DTO.Permissao.PermissaoDTO;
import lombok.Data;

import java.util.List;

@Data
public class GrupoDTO {

    private Long id;
    private String nome;

    private List<PermissaoDTO> permissoes;
}
