package com.algaworks.algafoodapi2.domain.exception.NotFound;

public class ProdutoNotFoundException extends EntityNotFoundException {
    public ProdutoNotFoundException(Long id) {
        super("Produto não encontrado com id: " + id);
    }
}