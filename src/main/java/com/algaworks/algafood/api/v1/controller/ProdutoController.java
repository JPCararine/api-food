package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoInputDTO;
import com.algaworks.algafood.domain.service.ProdutoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        return ResponseEntity.ok(produtoService.findAll());
    }
    @GetMapping("filtro")
    public ResponseEntity<List<ProdutoDTO>> findByFiltro(@RequestParam(required = false) String nome, @RequestParam(
            required = false) BigDecimal precoInicial, @RequestParam(required = false) BigDecimal precoFinal) {
        return ResponseEntity.ok(produtoService.find(nome, precoInicial, precoFinal));
    }
    @PostMapping
    public ResponseEntity<ProdutoDTO> save(@RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        return new ResponseEntity<>(produtoService.save(produtoInputDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        produtoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable long id, @RequestBody Map<String, Object> campos) {
        return ResponseEntity.ok(produtoService.merge(campos, id));


    }
}
