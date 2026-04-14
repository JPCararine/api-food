package com.algaworks.algafood.api.v1.DTO.Pedido;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PedidoVendasDiariasDTO {

    private LocalDate data;
    private Long totalVendas;
    private BigDecimal totalFaturado;

}


