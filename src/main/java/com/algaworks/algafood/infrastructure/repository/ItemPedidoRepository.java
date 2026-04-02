package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
