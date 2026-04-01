package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>,  ProdutoRepositoryQueries{


    List<Produto> preco(BigDecimal preco);

    List<Produto> findByIdIn(List<Long> ids);



}

