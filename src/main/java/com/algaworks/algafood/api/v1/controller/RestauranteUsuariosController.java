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
    @PutMapping("/{usuarioId}/cohost")
    @CheckSecurity.Restaurantes.PodeGerenciarTodosCargos
    public ResponseEntity<String> adicionarCohost(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteUsuarioService.adicionarCOHOST(restauranteId, usuarioId);
        return ResponseEntity.ok("Usuário adicionado com sucesso");
    }
    @DeleteMapping("/{usuarioId}/cohost")
    @CheckSecurity.Restaurantes.PodeGerenciarTodosCargos
    public ResponseEntity<String> removerCohostOuColaborador(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteUsuarioService.removerCOHOSTOuColaborador(restauranteId, usuarioId);
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }
    @PutMapping("/{usuarioId}/colaborador")
    @CheckSecurity.Restaurantes.PodeGerenciarColaboradores
    public ResponseEntity<String> adicionarColaborador(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteUsuarioService.adicionarCOLABORADOR(restauranteId, usuarioId);
        return ResponseEntity.ok("Usuário adicionado com sucesso");
    }
    @DeleteMapping("/{usuarioId}/colaborador")
    @CheckSecurity.Restaurantes.PodeGerenciarColaboradores
    public ResponseEntity<String> removerColaborador(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteUsuarioService.removerCOLABORADOR(restauranteId, usuarioId);
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }

}
