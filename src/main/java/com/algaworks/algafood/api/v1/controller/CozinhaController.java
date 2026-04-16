package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Cozinha.CozinhaInputDTO;
import com.algaworks.algafood.api.v1.DTO.Restaurante.CozinhaDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.CozinhaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController {

    private final CozinhaService cozinhaService;

    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @GetMapping
    @CheckSecurity.Cozinhas.PodeConsultar
    public ResponseEntity<Page<CozinhaDTO>> list(Pageable pageable) {

        return ResponseEntity.ok(cozinhaService.findAll(pageable));
    }
    @GetMapping("/{id}")
    @CheckSecurity.Cozinhas.PodeConsultar
    public ResponseEntity<CozinhaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cozinhaService.findById(id));
    }
    @GetMapping("/nomes")
    @CheckSecurity.Cozinhas.PodeConsultar
    public ResponseEntity<Page<CozinhaDTO>> findByNome(@RequestParam String nome, Pageable pageable) {
        return ResponseEntity.ok(cozinhaService.findByNome(nome, pageable));
    }
    @GetMapping("/nomes/busca")
    @CheckSecurity.Cozinhas.PodeConsultar
    public ResponseEntity<List<CozinhaDTO>> findByNomeBusca(@RequestParam String nome) {
        return ResponseEntity.ok(cozinhaService.findByNomeContaningIgnoreCase(nome));
    }

    @PostMapping
    @CheckSecurity.Cozinhas.PodeEditar
    public ResponseEntity<CozinhaDTO> save(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
        return new ResponseEntity<>(cozinhaService.save(cozinhaInputDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @CheckSecurity.Cozinhas.PodeEditar
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cozinhaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    @CheckSecurity.Cozinhas.PodeEditar
    public ResponseEntity<CozinhaDTO> put(@PathVariable long id, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO ) {
        return ResponseEntity.ok(cozinhaService.replace(id, cozinhaInputDTO));
    }
}
