package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Permissao.PermissaoDTO;
import com.algaworks.algafood.api.DTO.Permissao.UsuarioInputDTO;
import com.algaworks.algafood.domain.service.PermissaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("permissoes")
@AllArgsConstructor
public class PermissaoController {

    private final PermissaoService permissaoService;

    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> findAll() {
        return ResponseEntity.ok(permissaoService.listAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PermissaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(permissaoService.findById(id));
    }
    @PostMapping
    public ResponseEntity<PermissaoDTO> save(@RequestBody @Valid UsuarioInputDTO permissaoInputDTO) {
        return ResponseEntity.ok(permissaoService.save(permissaoInputDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<PermissaoDTO> update(@RequestBody @Valid UsuarioInputDTO permissaoInputDTO, @PathVariable Long id) {
        return ResponseEntity.ok(permissaoService.update(permissaoInputDTO, id));
    }
}
