package com.algaworks.algafoodapi2.repository;

import com.algaworks.algafoodapi2.domain.model.Produto;
import com.algaworks.algafoodapi2.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepositoryQueries {
    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
    Optional<Restaurante> acharPrimeiro();
}
