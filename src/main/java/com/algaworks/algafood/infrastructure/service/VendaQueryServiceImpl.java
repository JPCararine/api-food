package com.algaworks.algafood.infrastructure.service;


import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoVendasDiariasDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoVendasDiariasFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<PedidoVendasDiariasDTO> consultaVendasDiarias(PedidoVendasDiariasFilter filter, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query =  builder.createQuery(PedidoVendasDiariasDTO.class);
        var root = query.from(Pedido.class);
        var predicates = new ArrayList<Predicate>();
        var dataConvertida = builder.function(
                "coalesce",
                Date.class,
                builder.function(
                "convert_tz",
                Date.class,
                root.get("dataCriacao"),
                builder.literal("+00:00"),
                builder.literal(timeOffset)
                ),
                root.get("dataCriacao")
        );
        var functionDateDataCriacao = builder.function("date", Date.class, dataConvertida);

        var selection = builder.construct(PedidoVendasDiariasDTO.class,
                functionDateDataCriacao, builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
                );
        predicates.add(
                builder.in(root.get("status"))
                        .value(StatusPedido.CONFIRMADO)
                        .value(StatusPedido.ENTREGUE)
        );

        if(filter.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante").get("id"), filter.getRestauranteId()));
        }
        if(filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
        }
        if(filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
        }

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
