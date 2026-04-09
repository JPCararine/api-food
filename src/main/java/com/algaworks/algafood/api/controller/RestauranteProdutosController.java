package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.DTO.FotoProdutoDTO.FotoProdutoDTO;
import com.algaworks.algafood.api.DTO.FotoProdutoDTO.FotoProdutoInput;
import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoAndRestauranteNotFoundException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.*;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
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
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@AllArgsConstructor
public class RestauranteProdutosController {

    private RestauranteProdutoService restauranteProdutoService;
    private ProdutoService produtoService;
    private FotoProdutoService fotoProdutoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restauranteProdutoService.listarProdutos(restauranteId));
    }
    @GetMapping("{produtoId}")
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
    @PutMapping(value = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoDTO> atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @ModelAttribute @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        return ResponseEntity.ok(fotoProdutoService.atualizarFoto(restauranteId, produtoId, fotoProdutoInput));
    }
    @PutMapping("/{produtoId}/adicionar")
    public ResponseEntity<Void> adicionarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        restauranteProdutoService.adicionarProduto(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{produtoId}/remover")
    public ResponseEntity<Void> removerProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        restauranteProdutoService.removerProduto(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{produtoId}/foto")
    public ResponseEntity<Void> deletarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        fotoProdutoService.delete(restauranteId, produtoId);
        return ResponseEntity.noContent().build();
    }

}
