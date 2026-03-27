package com.algaworks.algafood.domain.exception.NotFound;

public class ProdutoNotFoundException extends BaseEntityNotFoundException {
    public ProdutoNotFoundException(Long id) {
        super("Produto não encontrado com id: " + id);
    }
}