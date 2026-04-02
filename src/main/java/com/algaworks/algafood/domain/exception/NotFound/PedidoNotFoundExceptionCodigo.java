package com.algaworks.algafood.domain.exception.NotFound;

public class PedidoNotFoundExceptionCodigo extends BaseEntityNotFoundException {
    public PedidoNotFoundExceptionCodigo(String codigo) {

        super("Pedido de id " + codigo + " não encontrado");
    }

}
