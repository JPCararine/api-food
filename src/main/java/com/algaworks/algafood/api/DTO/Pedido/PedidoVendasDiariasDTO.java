package com.algaworks.algafood.api.DTO.Pedido;

import com.algaworks.algafood.api.DTO.ItemPedido.ItemPedidoPrecoTotal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class PedidoVendasDiariasDTO {

    private LocalDate data;
    private Long totalVendas;
    private BigDecimal totalFaturado;

}


