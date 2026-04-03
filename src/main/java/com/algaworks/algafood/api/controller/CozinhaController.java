package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Cozinha.CozinhaInputDTO;
import com.algaworks.algafood.api.DTO.Restaurante.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
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
    public ResponseEntity<Page<CozinhaDTO>> list(Pageable pageable) {

        return ResponseEntity.ok(cozinhaService.findAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CozinhaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cozinhaService.findById(id));
    }
    @GetMapping("/nomes")
    public ResponseEntity<Page<CozinhaDTO>> findByNome(@RequestParam String nome, Pageable pageable) {
        return ResponseEntity.ok(cozinhaService.findByNome(nome, pageable));
    }
    @GetMapping("/nomes/busca")
    public ResponseEntity<List<CozinhaDTO>> findByNomeBusca(@RequestParam String nome) {
        return ResponseEntity.ok(cozinhaService.findByNomeContaningIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<CozinhaDTO> save(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
        return new ResponseEntity<>(cozinhaService.save(cozinhaInputDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cozinhaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CozinhaDTO> put(@PathVariable long id, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO ) {
        return ResponseEntity.ok(cozinhaService.replace(id, cozinhaInputDTO));
    }
}
