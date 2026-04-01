package com.algaworks.algafood.api.DTO.Grupo;

import com.algaworks.algafood.api.DTO.Permissao.PermissaoIdInputDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoIdInputDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
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
