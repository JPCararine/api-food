package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.RestauranteFormaPagamentoService;
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
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<List<String>> listar(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteFormaPagamentoServiceService.listarFormaPagamentos(restauranteId));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    public ResponseEntity<Void> removerFormas(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteFormaPagamentoServiceService.removerFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{formaPagamentoId}")
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    public ResponseEntity<Void> adicionarFormas(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteFormaPagamentoServiceService.adicionarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}
