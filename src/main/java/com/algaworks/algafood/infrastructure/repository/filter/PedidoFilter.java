package com.algaworks.algafood.infrastructure.repository.filter;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PedidoFilter  {

    private Long usuarioId;
    private Long restauranteId;
    private OffsetDateTime dataCriacaoInicio;
    private OffsetDateTime dataCriacaoFim;
}
