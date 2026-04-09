package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.FormaPagamento.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.RestauranteFormaPagamentoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@RestController
@AllArgsConstructor
public class RestauranteFormaPagamentoController {

    private final RestauranteFormaPagamentoService restauranteFormaPagamentoServiceService;


    @GetMapping
    public ResponseEntity<List<String>> listar(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteFormaPagamentoServiceService.listarFormaPagamentos(restauranteId));
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> removerFormas(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteFormaPagamentoServiceService.removerFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> adicionarFormas(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteFormaPagamentoServiceService.adicionarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}
