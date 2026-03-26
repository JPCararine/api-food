package com.algaworks.algafoodapi2.controller;

import com.algaworks.algafoodapi2.domain.model.Cidade;
import com.algaworks.algafoodapi2.domain.model.Cozinha;
import com.algaworks.algafoodapi2.DTO.CozinhaDTO;
import com.algaworks.algafoodapi2.DTO.CozinhaDTOPUT;
import com.algaworks.algafoodapi2.domain.model.Restaurante;
import com.algaworks.algafoodapi2.service.CozinhaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController {

    private final CozinhaService cozinhaService;

    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @GetMapping()
    public ResponseEntity<List<Cozinha>> list() {
        return ResponseEntity.ok(cozinhaService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cozinhaService.findById(id));
    }
    @GetMapping("/nomes")
    public ResponseEntity<List<Cozinha>> findByNome(@RequestParam String nome) {
        return ResponseEntity.ok(cozinhaService.findbyNome(nome));
    }
    @GetMapping("/nomes/busca")
    public ResponseEntity<List<Cozinha>> findByNomeBusca(@RequestParam String nome) {
        return ResponseEntity.ok(cozinhaService.findByNomeContaningIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<Cozinha> save(@RequestBody @Valid Cozinha cozinha) {
        return new ResponseEntity<>(cozinhaService.save(cozinha), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        cozinhaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> put(@PathVariable long id, @RequestBody @Valid Cozinha cozinhaRequest ) {
        return new ResponseEntity<>(cozinhaService.replace(id, cozinhaRequest), HttpStatus.NO_CONTENT);
    }
}
