package com.algaworks.algafoodapi2.domain.exception.NotFound;


public class CozinhaNotFoundException extends EntityNotFoundException {
    public CozinhaNotFoundException(Long id) {
        super("Cozinha não encontrada com id: " + id);
    }
}