package com.algaworks.algafood.domain.exception.NotFound;

public class CidadeNotFoundException extends BaseEntityNotFoundException {
    public CidadeNotFoundException(Long id) {
        super("Cidade não encontrada com id: " + id);
    }
}

