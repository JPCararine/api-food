package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoInputDTO;
import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/pedidos")
@AllArgsConstructor
public class RestaurantePedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    @CheckSecurity.Pedidos.PodeConsultar
    public ResponseEntity<List<PedidoResumoDTO>> listar(@PathVariable Long restauranteId, PedidoFilter pedidoFilter) {
        return ResponseEntity.ok(pedidoService.consultaFiltroNormal(restauranteId, pedidoFilter));
    }
    @GetMapping("/{codigoPedido}")
    @CheckSecurity.Pedidos.PodeConsultar
    public ResponseEntity<PedidoResumoDTO> buscar(@PathVariable String codigoPedido) {
        return ResponseEntity.ok(pedidoService.findByCodigo(codigoPedido));
    }
    @PutMapping("/{codigoPedido}/confirmacao")
    @CheckSecurity.Pedidos.PodeGerenciar
    public ResponseEntity<Void> confirmar(@PathVariable Long restauranteId, @PathVariable String codigoPedido) {
        pedidoService.confirmar(restauranteId,codigoPedido);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{codigoPedido}/entregar")
    @CheckSecurity.Pedidos.PodeGerenciar
    public ResponseEntity<Void> entregar(@PathVariable Long restauranteId, @PathVariable String codigoPedido) {
        pedidoService.entregar(restauranteId,codigoPedido);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{codigoPedido}/cancelar")
    @CheckSecurity.Pedidos.PodeGerenciar
    public ResponseEntity<Void> cancelar(@PathVariable Long restauranteId, @PathVariable String codigoPedido) {
        pedidoService.cancelar(restauranteId,codigoPedido);
        return ResponseEntity.noContent().build();
    }





}