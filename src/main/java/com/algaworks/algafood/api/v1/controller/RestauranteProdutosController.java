package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.FotoProdutoDTO.FotoProdutoDTO;
import com.algaworks.algafood.api.v1.DTO.FotoProdutoDTO.FotoProdutoInput;
import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoInputDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoAndRestauranteNotFoundException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@AllArgsConstructor
public class RestauranteProdutosController {

    private RestauranteProdutoService restauranteProdutoService;
    private ProdutoService produtoService;
    private FotoProdutoService fotoProdutoService;

    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteProdutoService.listarProdutos(restauranteId));
    }
    @GetMapping("{produtoId}")
    @CheckSecurity.Restaurantes.PodeConsultar
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(produtoService.findByRestaurante(restauranteId, produtoId));
    }
    @GetMapping("/{produtoId}/foto")
    public ResponseEntity<?> recuperar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                                         @RequestHeader(name = "Accept") String accept) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto foto = fotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

            MediaType mediaType = MediaType.parseMediaType(foto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(accept);

            boolean compativel = mediaTypesAceitas.stream()
                    .anyMatch(m -> m.isCompatibleWith(mediaType));
            if(!compativel) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }

            StorageService.FotoRecuperada fotoRecuperada = fotoProdutoService.download(restauranteId, produtoId);

            if(fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "inline; filename=\"" + foto.getNomeArquivo() + "\"")
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        } catch (ProdutoAndRestauranteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }


    }
    @CheckSecurity.Restaurantes.PodeGerenciar
    @PutMapping(value = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoDTO> atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @ModelAttribute @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        return ResponseEntity.ok(fotoProdutoService.atualizarFoto(restauranteId, produtoId, fotoProdutoInput));
    }
    @PostMapping
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<Void> adicionarProduto(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        restauranteProdutoService.adicionarProduto(restauranteId, produtoInputDTO);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{produtoId}/remover")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<Void> removerProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        restauranteProdutoService.removerProduto(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{produtoId}/foto")
    @CheckSecurity.Restaurantes.PodeGerenciar
    public ResponseEntity<Void> deletarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        fotoProdutoService.delete(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }

}
