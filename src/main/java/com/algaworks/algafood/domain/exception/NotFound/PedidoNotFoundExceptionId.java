package com.algaworks.algafood.domain.exception.NotFound;

public class PedidoNotFoundExceptionId extends BaseEntityNotFoundException{
    public PedidoNotFoundExceptionId(Long id) {

        super("Pedido de id " + id + " não encontrado");
    }
}
