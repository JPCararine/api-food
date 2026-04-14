package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoVendasDiariasDTO;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoVendasDiariasFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendaQueryService {

    List<PedidoVendasDiariasDTO> consultaVendasDiarias(PedidoVendasDiariasFilter filter, String timeOffset);
}
