package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.api.v1.DTO.UsuarioRestaurante.UsuarioRestauranteResumoDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.RestauranteUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
@AllArgsConstructor
public class RestauranteUsuariosController {

    private final RestauranteUsuarioService restauranteUsuarioService;

    @GetMapping
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<Set<UsuarioRestauranteResumoDTO>> listarUsuarios(@PathVariable long restauranteId) {
        return ResponseEntity.ok(restauranteUsuarioService.listarUsuarios(restauranteId));
    }
    @PutMapping("/{responsavelId}")
    @CheckSecurity.Restaurantes.PodeAdicionarCargos
    public ResponseEntity<String> adicionarCOHOST(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteUsuarioService.adicionarCOHOST(restauranteId, responsavelId);
        return ResponseEntity.ok("Usuário adicionado com sucesso");
    }
    @DeleteMapping("/{responsavelId}")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<String> removerCOHOST(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteUsuarioService.removerResponsavel(restauranteId, responsavelId);
        return ResponseEntity.ok("Usuário removido com sucesso.");



    }
}
