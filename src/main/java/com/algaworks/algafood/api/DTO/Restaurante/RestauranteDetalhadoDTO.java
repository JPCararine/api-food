package com.algaworks.algafood.api.DTO.Restaurante;

import com.algaworks.algafood.api.DTO.Endereco.EnderecoDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@JsonPropertyOrder({"id", "nome", "frete",  "ativo", "aberto", "cozinha", "endereco"})
@Data
public class RestauranteDetalhadoDTO {

    private Long id;

    private String nome;

    private BigDecimal frete;

    private Boolean ativo;
    private Boolean aberto;

    private CozinhaDTO cozinha;

    private EnderecoDTO endereco;

    private List<String> formaPagamentos;

    private List<ProdutoDTO> produtos;

    private Set<UsuarioIdNomeEmailDTO> responsaveis;


}
