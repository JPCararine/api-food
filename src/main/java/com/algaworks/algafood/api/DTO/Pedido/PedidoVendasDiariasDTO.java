package com.algaworks.algafood.api.DTO.Pedido;

import com.algaworks.algafood.api.DTO.ItemPedido.ItemPedidoPrecoTotal;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoVendasDiariasDTO {

    private LocalDate data;
    private Long totalVendas;
    private ItemPedidoPrecoTotal totalFaturado;





}


