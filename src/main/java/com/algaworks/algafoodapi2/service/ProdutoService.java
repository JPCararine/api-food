package com.algaworks.algafoodapi2.service;

import com.algaworks.algafoodapi2.domain.exception.NotFound.ProdutoNotFoundException;
import com.algaworks.algafoodapi2.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafoodapi2.domain.model.Produto;
import com.algaworks.algafoodapi2.domain.model.Restaurante;
import com.algaworks.algafoodapi2.repository.ProdutoRepository;
import com.algaworks.algafoodapi2.repository.RestauranteRepository;
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
    public Produto save(Produto produtoRequest) {
        corrigirRelacionamento(produtoRequest);
        Produto produto = Produto.builder()
                .nome(produtoRequest.getNome())
                .descricao(produtoRequest.getDescricao())
                .ativo(true)
                .preco(produtoRequest.getPreco())
                .restaurante(produtoRequest.getRestaurante())
                .build();
        return produtoRepository.save(produto);
    }
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
