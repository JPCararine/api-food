package com.algaworks.algafoodapi2.repository;

import com.algaworks.algafoodapi2.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>,  ProdutoRepositoryQueries{


    List<Produto> preco(BigDecimal preco);


}

