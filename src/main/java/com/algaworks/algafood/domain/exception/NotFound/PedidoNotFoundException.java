package com.algaworks.algafood.domain.exception.NotFound;

public class PedidoNotFoundException extends BaseEntityNotFoundException {
    public PedidoNotFoundException(Long id) {
        super("Pedido de id " + id + " não encontrado");
    }
}
