package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("produtos")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        return ResponseEntity.ok(produtoService.findAll());
    }
    @GetMapping("filtro")
    public ResponseEntity<List<Produto>> findByFiltro(@RequestParam(required = false) String nome, @RequestParam(
            required = false) BigDecimal precoInicial, @RequestParam(required = false) BigDecimal precoFinal) {
        return ResponseEntity.ok(produtoService.find(nome, precoInicial, precoFinal));
    }
    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody Produto produto) {
        return new ResponseEntity<>(produtoService.save(produto), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        produtoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable long id, @RequestBody Map<String, Object> campos) {
        Produto produtoAtual =  produtoService.findById(id);

        produtoService.merge(campos, produtoAtual);

        produtoService.corrigirRelacionamento(produtoAtual);

        return ResponseEntity.ok(produtoService.update(produtoAtual));


    }
}
