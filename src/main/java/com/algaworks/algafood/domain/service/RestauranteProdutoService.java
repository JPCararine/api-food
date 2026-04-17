package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.v1.DTO.Produto.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoInputDTODisassembler;
import com.algaworks.algafood.domain.exception.JaExistente.FormaPagamentoJaExistente;
import com.algaworks.algafood.domain.exception.JaExistente.ProdutoJaExistente;
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
    private final ProdutoInputDTODisassembler  produtoInputDTODisassembler;


    public List<ProdutoDTO> listarProdutos(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        return produtoRepository.findByRestauranteAndAtivoTrue(restaurante)
                .stream()
                .map(produtoDTOAssembler::toDTO)
                .toList();

    }
    public void adicionarProduto(Long restauranteId, ProdutoInputDTO dto) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        Produto produto = produtoInputDTODisassembler.toEntity(dto);

        if(restaurante.getProdutos().contains(produto)) {
            throw new ProdutoJaExistente();
        }
        produto.setRestaurante(restaurante);

        produtoRepository.save(produto);


    }
    public void removerProduto(Long restauranteId, Long produtoId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        Produto produto = buscarProdutoOuFalhar(produtoId);
        produto.setAtivo(false);

        produtoRepository.save(produto);



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
