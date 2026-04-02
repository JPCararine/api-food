package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @EntityGraph(attributePaths = "itens")
    List<Pedido> findByRestauranteId(Long restauranteId);

}
