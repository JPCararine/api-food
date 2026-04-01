package com.algaworks.algafood.api.DTO.Grupo;

import com.algaworks.algafood.api.DTO.Permissao.PermissaoDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import lombok.Data;

import java.util.List;

@Data
public class GrupoDTO {

    private Long id;
    private String nome;

    private List<PermissaoDTO> permissoes;
}
