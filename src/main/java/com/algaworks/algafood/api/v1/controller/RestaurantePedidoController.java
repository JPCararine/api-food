package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoInputDTO;
import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/pedidos")
@AllArgsConstructor
public class RestaurantePedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResumoDTO>> listar(@PathVariable Long restauranteId, PedidoFilter pedidoFilter) {
        return ResponseEntity.ok(pedidoService.consultaFiltroNormal(restauranteId, pedidoFilter));
    }

    @PostMapping
    public ResponseEntity<PedidoResumoDTO> criar(@PathVariable Long restauranteId, @RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        return new ResponseEntity<>(pedidoService.criar(restauranteId, pedidoInputDTO), HttpStatus.CREATED);
    }
    @PutMapping("/{codigoPedido}/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
        pedidoService.confirmar(codigoPedido);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{codigoPedido}/entregar")
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
        pedidoService.entregar(codigoPedido);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{codigoPedido}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
        pedidoService.cancelar(codigoPedido);
        return ResponseEntity.noContent().build();
    }





}