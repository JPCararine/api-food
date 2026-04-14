package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.domain.service.RestauranteUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
@AllArgsConstructor
public class RestauranteUsuariosController {

    private final RestauranteUsuarioService restauranteUsuarioService;

    @GetMapping
    public ResponseEntity<Set<UsuarioIdNomeEmailDTO>> listarUsuarios(@PathVariable long restauranteId) {
        return ResponseEntity.ok(restauranteUsuarioService.listarUsuarios(restauranteId));
    }
    @PutMapping("/{responsavelId}")
    public ResponseEntity<String> adicionarResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteUsuarioService.adicionarResponsavel(restauranteId, responsavelId);
        return ResponseEntity.ok("Usuário adicionado com sucesso.");
    }
    @DeleteMapping("/{responsavelId}")
    public ResponseEntity<String> removerResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteUsuarioService.removerResponsavel(restauranteId, responsavelId);
        return ResponseEntity.ok("Usuário removido com sucesso.");



    }
}
