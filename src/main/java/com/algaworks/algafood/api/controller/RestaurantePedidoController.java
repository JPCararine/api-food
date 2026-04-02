package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Pedido.PedidoInputDTO;
import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.infrastructure.repository.PedidoRepository;
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
    private final RestauranteService restauranteService;
    @GetMapping
    public ResponseEntity<List<PedidoResumoDTO>> listar(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteService.listarPedidos(restauranteId));
    }
    @PostMapping
    public ResponseEntity<PedidoResumoDTO> criar(@PathVariable Long restauranteId, @RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        return new ResponseEntity<>(pedidoService.criar(restauranteId, pedidoInputDTO), HttpStatus.CREATED);
    }
    @PutMapping("/{pedidoId}/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable Long pedidoId) {
        pedidoService.confirmar(pedidoId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{pedidoId}/entregar")
    public ResponseEntity<Void> entregar(@PathVariable Long pedidoId) {
        pedidoService.entregar(pedidoId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{pedidoId}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long pedidoId) {
        pedidoService.cancelar(pedidoId);
        return ResponseEntity.noContent().build();
    }




}