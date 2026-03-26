package com.algaworks.algafoodapi2.infrastructure.repository;

import com.algaworks.algafoodapi2.domain.model.Produto;
import com.algaworks.algafoodapi2.repository.ProdutoRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {
    @PersistenceContext
    private EntityManager manager;
    public List<Produto> find(String nome, BigDecimal precoInicial, BigDecimal precoFinal) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
        Root<Produto> root = criteria.from(Produto.class);

        var predicates = new ArrayList<Predicate>();
        if (StringUtils.hasText(nome)) {
            predicates.add(builder.equal(root.get("nome"), "%" + nome + "%"));
        }
        if(precoInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("preco"), precoInicial));
        }
        if(precoFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("preco"), precoFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        var query = manager.createQuery(criteria);
        return query.getResultList();


    }


}
