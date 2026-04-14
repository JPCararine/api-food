package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {
    @EntityGraph(attributePaths = "itens")
    List<Pedido> findByRestauranteId(Long restauranteId);

    boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);

    Optional<Pedido> findByCodigo(String codigo);

}
