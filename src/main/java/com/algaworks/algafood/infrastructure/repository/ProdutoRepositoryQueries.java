package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Produto;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepositoryQueries {

    List<Produto> find(String nome, BigDecimal precoInicial, BigDecimal precoFinal);

}
