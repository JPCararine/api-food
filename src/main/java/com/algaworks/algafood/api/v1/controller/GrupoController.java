package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Grupo.GrupoDTO;
import com.algaworks.algafood.api.v1.DTO.Grupo.GrupoInputDTO;
import com.algaworks.algafood.domain.service.GrupoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/grupo")
@RestController
@RequiredArgsConstructor
public class GrupoController {



    private final GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> findAll() {

        return ResponseEntity.ok().body(grupoService.listAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GrupoDTO> findById(@PathVariable Long id){
        return  ResponseEntity.ok().body(grupoService.findById(id));
    }
    @PostMapping
    public ResponseEntity<GrupoDTO> post(@RequestBody @Valid GrupoInputDTO grupoInputDTO){
        return new ResponseEntity<>(grupoService.save(grupoInputDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        grupoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<GrupoDTO> put(@RequestBody @Valid GrupoInputDTO grupoInputDTO, @PathVariable Long id){
        return ResponseEntity.ok(grupoService.update(grupoInputDTO, id));
    }


}
