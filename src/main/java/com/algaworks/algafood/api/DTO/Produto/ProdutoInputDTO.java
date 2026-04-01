package com.algaworks.algafood.api.DTO.Produto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoInputDTO {

    private String nome;
    private String descricao;
    private BigDecimal preco;
}
