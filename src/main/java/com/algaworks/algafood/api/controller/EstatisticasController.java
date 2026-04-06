package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Pedido.PedidoVendasDiariasDTO;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoVendasDiariasFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("estatisticas")
@RequiredArgsConstructor
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public ResponseEntity<List<PedidoVendasDiariasDTO>> consultarVendasDiarias(PedidoVendasDiariasFilter filter) {
        return ResponseEntity.ok(vendaQueryService.consultaVendasDiarias(filter));
    }
}
