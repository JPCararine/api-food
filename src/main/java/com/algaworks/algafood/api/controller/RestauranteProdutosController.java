package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@AllArgsConstructor
public class RestauranteProdutosController {

    private RestauranteService restauranteService;
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteService.listarProdutos(restauranteId));
    }
    @GetMapping("{produtoId}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(produtoService.findByRestaurante(restauranteId, produtoId));
    }
    @PutMapping("/{produtoId}/adicionar")
    public ResponseEntity<Void> adicionarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        restauranteService.adicionarProduto(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{produtoId}/remover")
    public ResponseEntity<Void> removerProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        restauranteService.removerProduto(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }
}
