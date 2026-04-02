package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTO;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTOPut;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDetalhadoDTO;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<RestauranteDTO>> list() {
        return ResponseEntity.ok(restauranteService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDetalhadoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(restauranteService.findByIdDetalhado(id));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<RestauranteDTO>> consultaPorNomeEId(
            @RequestParam String nome,
            @RequestParam Long id) {
        return ResponseEntity.ok(restauranteService.findByNome(nome));
    }

    @GetMapping("/por-nome-e-frete")
    public ResponseEntity<List<RestauranteDTO>> find(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal taxaFreteInicial,
            @RequestParam(required = false) BigDecimal taxaFreteFinal) {
        return ResponseEntity.ok(restauranteService.find(nome, taxaFreteInicial, taxaFreteFinal));
    }

    @GetMapping("/com-frete-gratis")
    public ResponseEntity<List<RestauranteDTO>> findFreteGratis(
            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(restauranteService.findTaxaGratis(nome));
    }

    @GetMapping("/primeiro")
    public ResponseEntity<Optional<RestauranteDTO>> primeiroRestaurante() {
        return ResponseEntity.ok(restauranteService.primeiroRestaurante());
    }

    @PostMapping
    public ResponseEntity<RestauranteDTO> save(
            @RequestBody @Valid RestauranteDTOPut dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        restauranteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> put(
            @PathVariable Long id,
            @RequestBody @Valid RestauranteDTOPut dto) {
        return ResponseEntity.ok(restauranteService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestauranteDTO> patch(
            @PathVariable Long id,
            @RequestBody Map<String, Object> campos) {
        return ResponseEntity.ok(restauranteService.patch(id, campos));
    }
    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        restauranteService.desativar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/abrir")
    public ResponseEntity<Void> abrir(@PathVariable Long id) {
        restauranteService.abrir(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}/fechar")
    public ResponseEntity<Void> fechar(@PathVariable Long id) {
        restauranteService.fechar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/ativacoes")
    public ResponseEntity<Void> ativarVarios(@RequestBody List<Long> ids) {
        restauranteService.ativarVarios(ids);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/desativacoes")
    public ResponseEntity<Void> desativarVarios(@RequestBody List<Long> ids) {
        restauranteService.desativarVarios(ids);
        return ResponseEntity.noContent().build();
    }


}