package com.algaworks.algafood.api.v1.DTO.ItemPedido;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
@JsonPropertyOrder({"produtoId", "produtoNome", "quantidade", "precoUnitario", "precoTotal", "observacao"})
@Data
public class ItemPedidoResumoDTO {

    private Long produtoId;
    private String produtoNome;

    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;

    private String observacao;
}