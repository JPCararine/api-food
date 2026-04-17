package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Restaurante.RestauranteDTO;
import com.algaworks.algafood.api.v1.DTO.Restaurante.RestauranteDTOPut;
import com.algaworks.algafood.api.v1.DTO.Restaurante.RestauranteDetalhadoDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<List<RestauranteDTO>> list() {
        return ResponseEntity.ok(restauranteService.listAll());
    }

    @GetMapping("/{restauranteId}")
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<RestauranteDetalhadoDTO> findById(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteService.findByIdDetalhado(restauranteId));
    }

    @GetMapping("/busca")
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<List<RestauranteDTO>> consultaPorNomeEId(
            @RequestParam String nome,
            @RequestParam Long id) {
        return ResponseEntity.ok(restauranteService.findByNome(nome));
    }

    @GetMapping("/abertos")
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<List<RestauranteDTO>> consultaAbertos() {
        return ResponseEntity.ok(restauranteService.listByAberto());
    }

    @GetMapping("/por-nome-e-frete")
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<List<RestauranteDTO>> find(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal taxaFreteInicial,
            @RequestParam(required = false) BigDecimal taxaFreteFinal) {
        return ResponseEntity.ok(restauranteService.find(nome, taxaFreteInicial, taxaFreteFinal));
    }

    @GetMapping("/com-frete-gratis")
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<List<RestauranteDTO>> findFreteGratis(
            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(restauranteService.findTaxaGratis(nome));
    }

    @GetMapping("/primeiro")
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<Optional<RestauranteDTO>> primeiroRestaurante() {
        return ResponseEntity.ok(restauranteService.primeiroRestaurante());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
    public ResponseEntity<RestauranteDTO> save(
            @RequestBody @Valid RestauranteDTOPut dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.save(dto));
    }

    @DeleteMapping("/{restauranteId}")
    @CheckSecurity.Restaurantes.PodeDeletarRestaurante
    public ResponseEntity<Void> deleteById(@PathVariable Long restauranteId) {
        restauranteService.delete(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<RestauranteDTO> put(
            @PathVariable Long restauranteId,
            @RequestBody @Valid RestauranteDTOPut dto) {
        return ResponseEntity.ok(restauranteService.update(restauranteId, dto));
    }

    @PatchMapping("/{restauranteId}")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<RestauranteDTO> patch(
            @PathVariable Long restauranteId,
            @RequestBody Map<String, Object> campos) {
        return ResponseEntity.ok(restauranteService.patch(restauranteId, campos));
    }

    @PutMapping("/{restauranteId}/ativar")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/desativar")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<Void> desativar(@PathVariable Long restauranteId) {
        restauranteService.desativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/abrir")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/fechar")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/abrirVarios")
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
    public ResponseEntity<Void> abrirVarios(@RequestBody List<Long> ids) {
        restauranteService.abrirVarios(ids);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ativacoes")
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
    public ResponseEntity<Void> ativarVarios(@RequestBody List<Long> ids) {
        restauranteService.ativarVarios(ids);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/desativacoes")
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
    public ResponseEntity<Void> desativarVarios(@RequestBody List<Long> ids) {
        restauranteService.desativarVarios(ids);
        return ResponseEntity.noContent().build();
    }
}

