package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NotFound.ProdutoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.ProdutoRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }
    public List<Produto> find(String nome, BigDecimal precoInicial, BigDecimal precoFinal) {
        return produtoRepository.find(nome, precoInicial, precoFinal);
    }
    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }
    @Transactional
    public Produto save(Produto produtoRequest) {
        corrigirRelacionamento(produtoRequest);
        Produto produto = Produto.builder()
                .nome(produtoRequest.getNome())
                .descricao(produtoRequest.getDescricao())
                .preco(produtoRequest.getPreco())
                .restaurante(produtoRequest.getRestaurante())
                .build();
        return produtoRepository.save(produto);
    }
    @Transactional
    public void delete(long id){
        produtoRepository.delete(findById(id));
    }
    public void merge(Map<String, Object> camposPassados, Produto produtoAtual) {

        ObjectMapper mapper = new ObjectMapper();
        Produto produto = mapper.convertValue(camposPassados, Produto.class);

        List<String> camposIgnorados = List.of("id");

        camposPassados.forEach((key, value) -> {
            if(camposIgnorados.contains(key)) {
                return;
            }
            Field campo = ReflectionUtils.findField(Produto.class, key);
            campo.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(campo, produto);

            ReflectionUtils.setField(campo, produtoAtual, novoValor);
        });

    }
    public Produto update(Produto produtoAtual) {
        return produtoRepository.save(produtoAtual);
    }
    public void corrigirRelacionamento(Produto produto) {

        if(produto.getRestaurante() != null) {
            Long restauranteId = produto.getRestaurante().getId();

            Restaurante restaurante = restauranteRepository.findById(restauranteId)
                    .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));
            produto.setRestaurante(restaurante);
        }

    }
}
