package com.algaworks.algafood.api.v1.DTO.ItemPedido;

import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoIdInputDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemPedidoInputDTO {

    @NotNull
    @Min(1)
    private Integer quantidade;


    private String observacao;


    @Valid
    @NotNull
    private ProdutoIdInputDTO produto;
}
