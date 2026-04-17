package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoInputDTO;
import com.algaworks.algafood.domain.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        return ResponseEntity.ok(produtoService.findAll());
    }
    @GetMapping("filtro")
    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
    public ResponseEntity<List<ProdutoDTO>> findByFiltro(@RequestParam(required = false) String nome, @RequestParam(
            required = false) BigDecimal precoInicial, @RequestParam(required = false) BigDecimal precoFinal) {
        return ResponseEntity.ok(produtoService.find(nome, precoInicial, precoFinal));
    }
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('GERENCIAR_PEDIDOS')")
    public ResponseEntity<ProdutoDTO> save(@RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        return new ResponseEntity<>(produtoService.save(produtoInputDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{produtoId}")
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('GERENCIAR_PEDIDOS')")
    public ResponseEntity<Void> delete(@PathVariable long produtoId) {
        produtoService.delete(produtoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{produtoId}")
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('GERENCIAR_PEDIDOS')")
    public ResponseEntity<?> patch(@PathVariable long produtoId, @RequestBody Map<String, Object> campos) {
        return ResponseEntity.ok(produtoService.merge(campos, produtoId));


    }
}
