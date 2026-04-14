package com.algaworks.algafood.api.v1.DTO.Produto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
@JsonPropertyOrder({"id", "nome", "descricao", "preco"})
@Data
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
}
