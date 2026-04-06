package com.algaworks.algafood.infrastructure.service;


import com.algaworks.algafood.api.DTO.Pedido.PedidoVendasDiariasDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoVendasDiariasFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<PedidoVendasDiariasDTO> consultaVendasDiarias(PedidoVendasDiariasFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query =  builder.createQuery(PedidoVendasDiariasDTO.class);
        var root = query.from(Pedido.class);

        var functionDateDataCriacao = builder.function("date", LocalDate.class, root.get("dataCriacao"));

        var selection = builder.construct(PedidoVendasDiariasDTO.class,
                functionDateDataCriacao, builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
                );

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
