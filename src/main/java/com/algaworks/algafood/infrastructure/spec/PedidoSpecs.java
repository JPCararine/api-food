package com.algaworks.algafood.infrastructure.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import jakarta.persistence.FetchType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;


public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return(root, query, builder) -> {
            if(Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("usuario");
                root.fetch("formaPagamento");
                root.fetch("itens").fetch("produto");
            }

            var predicates = new ArrayList<Predicate>();

            if(filtro.getUsuarioId() != null){
                predicates.add(builder.equal(root.get("usuario").get("id"), filtro.getUsuarioId()));
            }
            if(filtro.getRestauranteId() != null){
                predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
            }
            if(filtro.getDataCriacaoInicio() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
            }
            if(filtro.getDataCriacaoFim() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
