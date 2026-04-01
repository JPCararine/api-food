package com.algaworks.algafood.api.DTO.Restaurante;

import com.algaworks.algafood.api.DTO.Endereco.EnderecoDTO;
import com.algaworks.algafood.api.DTO.FormaPagamento.FormaPagamentoDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@JsonPropertyOrder({"id", "nome", "frete",  "ativo", "aberto", "cozinha", "endereco"})
@Data
public class RestauranteDetalhadoDTO {

    private Long id;

    private String nome;

    private BigDecimal frete;

    private Boolean ativo;

    private CozinhaDTO cozinha;

    private EnderecoDTO endereco;

    private List<String> formaPagamentos;

    private List<ProdutoDTO> produtos;


}
