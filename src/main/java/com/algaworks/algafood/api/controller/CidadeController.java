package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Cidade.CidadeDTO;
import com.algaworks.algafood.api.DTO.Cidade.CidadeInputDTO;
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
    public ResponseEntity<List<CidadeDTO>> findAll(){
        return ResponseEntity.ok(cidadeService.listAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> findById(@PathVariable long id){
        return ResponseEntity.ok(cidadeService.findById(id));
    }
    @PostMapping
    public ResponseEntity<CidadeDTO> save(@RequestBody @Valid CidadeInputDTO cidadeInputDTO){
        return new ResponseEntity<>(cidadeService.save(cidadeInputDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        cidadeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CidadeDTO> replace(@PathVariable long id, @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        return ResponseEntity.ok(cidadeService.replace(id, cidadeInputDTO));
    }
}
