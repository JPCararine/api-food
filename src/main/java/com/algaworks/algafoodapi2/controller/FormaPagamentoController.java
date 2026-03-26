package com.algaworks.algafoodapi2.controller;

import com.algaworks.algafoodapi2.domain.model.FormaPagamento;
import com.algaworks.algafoodapi2.service.FormaPagamentoService;
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
    public ResponseEntity<List<FormaPagamento>> findAll(){
        return ResponseEntity.ok(formaPagamentoService.findAll());
    }

    @PostMapping
    public ResponseEntity<FormaPagamento> save(@RequestBody FormaPagamento formaPagamento){
        return new ResponseEntity<>(formaPagamentoService.save(formaPagamento), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamento> findById(@PathVariable Long id){
        return  ResponseEntity.ok(formaPagamentoService.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        formaPagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
