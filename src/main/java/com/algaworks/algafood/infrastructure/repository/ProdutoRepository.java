package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long>,  ProdutoRepositoryQueries{


    List<Produto> preco(BigDecimal preco);

    List<Produto> findByIdIn(List<Long> ids);

    Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);

    @Query("select f from FotoProduto f join f.produto p " + "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId ")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);



}

