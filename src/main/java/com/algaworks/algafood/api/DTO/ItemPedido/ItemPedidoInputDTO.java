package com.algaworks.algafood.api.DTO.ItemPedido;

import com.algaworks.algafood.api.DTO.Produto.ProdutoIdInputDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoInputDTO {

    @NotNull
    @Min(1)
    private Integer quantidade;

    @NotBlank
    private String observacao;


    @Valid
    @NotNull
    private ProdutoIdInputDTO produto;
}
