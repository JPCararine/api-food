package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.FormaPagamento.FormaPagamentoDTO;
import com.algaworks.algafood.api.DTO.FormaPagamento.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("formas")
@AllArgsConstructor
public class FormaPagamentoController {

    private FormaPagamentoService formaPagamentoService;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> findAll(){
        return ResponseEntity.ok(formaPagamentoService.findAll());
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> save(@RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO){
        return new ResponseEntity<>(formaPagamentoService.save(formaPagamentoInputDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> findById(@PathVariable Long id){
        return  ResponseEntity.ok(formaPagamentoService.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        formaPagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> put(@PathVariable Long id, @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO){
        return new ResponseEntity<>(formaPagamentoService.update(formaPagamentoInputDTO, id), HttpStatus.OK);
    }


}
