package com.algaworks.algafoodapi2.controller;

import com.algaworks.algafoodapi2.DTO.RestauranteDTO;
import com.algaworks.algafoodapi2.DTO.RestauranteDTOPut;
import com.algaworks.algafoodapi2.domain.model.*;
import com.algaworks.algafoodapi2.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tools.jackson.core.util.RecyclerPool;

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
        public ResponseEntity<Restaurante> findById(@PathVariable Long id) {
            return ResponseEntity.ok(restauranteService.findById(id));
        }
        @GetMapping("/busca")
        public ResponseEntity<List<Restaurante>> consultaPorNomeEId(@RequestParam String nome,@RequestParam Long id) {
        return ResponseEntity.ok(restauranteService.consultarPorNome(nome, id));
        }
        @GetMapping("/por-nome-e-frete")
        public ResponseEntity<List<Restaurante>> find(@RequestParam(required = false) String nome, @RequestParam(required = false) BigDecimal taxaFreteInicial,
                                                  @RequestParam(required = false) BigDecimal taxaFreteFinal) {
            return ResponseEntity.ok(restauranteService.find(nome, taxaFreteInicial, taxaFreteFinal));
        }
        @GetMapping("com-frete-gratis")
        public ResponseEntity<?> findFreteGratis(@RequestParam(required = false) String nome) {
            return ResponseEntity.ok(restauranteService.findTaxaGratis(nome));
        }
        @GetMapping("primeiro")
        public ResponseEntity<Optional<Restaurante>> primeiroRestaurante() {
        return ResponseEntity.ok(restauranteService.primeiroRestaurante());
        }

//        @GetMapping("com-frete-gratis")
//        public ResponseEntity<?> findFreteGratis(@RequestParam(required = false) String nome,
//                                                                 @RequestParam(required = false) BigDecimal taxaFrete) {
//            try {
//                return ResponseEntity.ok(restauranteService.findTaxaGratis(nome, taxaFrete));
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//            }
//        }
        @PostMapping
        public ResponseEntity<Restaurante> save(@RequestBody @Valid Restaurante restaurante) {
            return new ResponseEntity<>(restauranteService.save(restaurante), HttpStatus.CREATED);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteById(@PathVariable Long id) {
            restauranteService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        @PutMapping("/{id}")
        public ResponseEntity<Restaurante> put(@PathVariable long id, @RequestBody @Valid Restaurante restauranteRequest ) {
            return ResponseEntity.ok(restauranteService.replace(id, restauranteRequest));
        }
        @PatchMapping("/{id}")
        public ResponseEntity<?> patch(@PathVariable long id, @RequestBody Map<String, Object> campos) {
            Restaurante restauranteAtual =  restauranteService.findById(id);

            restauranteService.merge(campos, restauranteAtual);

            restauranteService.corrigirRelacionamentos(restauranteAtual);

            return ResponseEntity.ok(restauranteService.update(restauranteAtual));


        }
    }

