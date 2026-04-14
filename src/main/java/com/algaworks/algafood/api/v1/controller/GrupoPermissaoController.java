package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Permissao.PermissaoDTO;
import com.algaworks.algafood.domain.service.GrupoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
@AllArgsConstructor
public class GrupoPermissaoController {

    private final GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> listar(@PathVariable Long grupoId) {
        return ResponseEntity.ok(grupoService.listarPermissoes(grupoId));

    }
    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> adicionarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.adicionarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> removerPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.removerPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}
