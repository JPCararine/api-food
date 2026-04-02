package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdNomeDTO;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
@AllArgsConstructor
public class RestauranteUsuariosController {

    private final RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<Set<UsuarioIdNomeDTO>> listarUsuarios(@PathVariable long restauranteId) {
        return ResponseEntity.ok(restauranteService.listarUsuarios(restauranteId));
    }
    @PutMapping("/{responsavelId}")
    public ResponseEntity<String> adicionarResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.adicionarResponsavel(restauranteId, responsavelId);
        return ResponseEntity.ok("Usuário adicionado com sucesso.");
    }
    @DeleteMapping("/{responsavelId}")
    public ResponseEntity<String> removerResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.removerResponsavel(restauranteId, responsavelId);
        return ResponseEntity.ok("Usuário removido com sucesso.");



    }
}
