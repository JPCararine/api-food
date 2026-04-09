package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.domain.exception.JaExistente.FormaPagamentoJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.FormaPagamentoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.ProdutoRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoDTOAssembler produtoDTOAssembler;


    public List<ProdutoDTO> listarProdutos(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        return restaurante.getProdutos()
                .stream()
                .map(produtoDTOAssembler::toDTO)
                .toList();

    }
    public void adicionarProduto(Long restauranteId, Long produtoId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        Produto produto = buscarProdutoOuFalhar(produtoId);

        if(restaurante.getProdutos().contains(produto)) {
            throw new FormaPagamentoJaExistente();
        }
        restaurante.getProdutos().add(produto);

    }
    public void removerProduto(Long restauranteId, Long produtoId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        Produto produto = buscarProdutoOuFalhar(produtoId);


        restaurante.getProdutos().remove(produto);

    }

    private Restaurante buscarRestauranteOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
    }
    private Produto buscarProdutoOuFalhar(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }

}
