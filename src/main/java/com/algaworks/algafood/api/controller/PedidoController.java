package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoAdminDTO;
import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.infrastructure.repository.PedidoRepository;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;


    @GetMapping ResponseEntity<Page<PedidoResumoAdminDTO>>  pesquisar(PedidoFilter  pedidoFilter, Pageable pageable) {
        return ResponseEntity.ok(pedidoService.consultaFiltroAdmin(pedidoFilter, pageable));
    }
    @GetMapping("/{codigoPedido}")
    public ResponseEntity<PedidoResumoDTO> buscar(@PathVariable String codigoPedido) {
        return ResponseEntity.ok(pedidoService.findByCodigo(codigoPedido));
    }







}
