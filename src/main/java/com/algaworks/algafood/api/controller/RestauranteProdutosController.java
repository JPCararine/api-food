package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.FotoProdutoDTO.FotoProdutoDTO;
import com.algaworks.algafood.api.DTO.Produto.FotoProdutoInput;
import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@AllArgsConstructor
public class RestauranteProdutosController {

    private RestauranteService restauranteService;
    private ProdutoService produtoService;
    private FotoProdutoService fotoProdutoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteService.listarProdutos(restauranteId));
    }
    @GetMapping("{produtoId}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(produtoService.findByRestaurante(restauranteId, produtoId));
    }
    @PutMapping(value = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoDTO> atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @ModelAttribute @Valid FotoProdutoInput fotoProdutoInput) {
        return ResponseEntity.ok(fotoProdutoService.atualizarFoto(restauranteId, produtoId, fotoProdutoInput));
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
