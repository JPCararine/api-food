package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Estado.EstadoDTO;
import com.algaworks.algafood.api.DTO.Estado.EstadoInputDTO;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CidadeService;
import com.algaworks.algafood.domain.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;
    private final CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll(){
        return ResponseEntity.ok(estadoService.listAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> findById(@PathVariable long id){
        return ResponseEntity.ok(estadoService.findById(id));
    }
    @PostMapping
    public ResponseEntity<EstadoDTO> save(@RequestBody @Valid EstadoInputDTO estadoInputDTO){
        return new ResponseEntity<>(estadoService.save(estadoInputDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        estadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> put(@PathVariable long id, @RequestBody @Valid EstadoInputDTO estadoInputDTO){
        return new ResponseEntity<>(estadoService.put(id, estadoInputDTO), HttpStatus.OK);
    }
}
