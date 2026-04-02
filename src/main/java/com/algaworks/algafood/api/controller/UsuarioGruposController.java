package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Grupo.GrupoDTO;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuariosId}/grupos")
@AllArgsConstructor
public class UsuarioGruposController {

    private final UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<GrupoDTO>> findAll(@PathVariable Long usuariosId) {
        return ResponseEntity.ok(usuarioService.listarGrupos(usuariosId));
    }
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> adicionarGrupo(@PathVariable Long grupoId, @PathVariable Long usuariosId) {
        usuarioService.adicionarGrupo(usuariosId, grupoId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> removerGrupo(@PathVariable Long grupoId, @PathVariable Long usuariosId) {
        usuarioService.removerGrupo(usuariosId, grupoId);
        return ResponseEntity.noContent().build();
    }

}
