package com.algaworks.algafoodapi2.domain.exception.NotFound;

public class CidadeNotFoundException extends EntityNotFoundException {
    public CidadeNotFoundException(Long id) {
        super("Cidade não encontrada com id: " + id);
    }
}

