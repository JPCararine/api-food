package com.algaworks.algafoodapi2.repository;

import com.algaworks.algafoodapi2.domain.model.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepositoryQueries {

    List<Produto> find(String nome, BigDecimal precoInicial, BigDecimal precoFinal);

}
