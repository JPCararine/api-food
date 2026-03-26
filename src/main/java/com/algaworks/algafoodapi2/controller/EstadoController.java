package com.algaworks.algafoodapi2.controller;

import com.algaworks.algafoodapi2.domain.model.Cidade;
import com.algaworks.algafoodapi2.domain.model.Estado;
import com.algaworks.algafoodapi2.repository.EstadoRepository;
import com.algaworks.algafoodapi2.service.CidadeService;
import com.algaworks.algafoodapi2.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;
    private final CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Estado>> findAll(){
        return ResponseEntity.ok(estadoService.listAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Estado> findById(@PathVariable long id){
        return ResponseEntity.ok(estadoService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Estado> save(@RequestBody @Valid Estado estado){
        return new ResponseEntity<>(estadoService.save(estado), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        estadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
