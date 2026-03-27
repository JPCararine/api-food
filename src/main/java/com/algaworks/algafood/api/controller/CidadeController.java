package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> findAll(){
        return ResponseEntity.ok(cidadeService.listAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cidade> findById(@PathVariable long id){
        return ResponseEntity.ok(cidadeService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Cidade> save(@RequestBody @Valid  Cidade cidade){
        return new ResponseEntity<>(cidadeService.save(cidade), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        cidadeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cidade> replace(@PathVariable long id, @RequestBody @Valid Cidade cidadeRequest) {
        return ResponseEntity.ok(cidadeService.replace(id, cidadeRequest));
    }
}
