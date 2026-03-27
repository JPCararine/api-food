package com.algaworks.algafood.domain.exception.NotFound;


public class CozinhaNotFoundException extends BaseEntityNotFoundException {
    public CozinhaNotFoundException(Long id) {
        super("Cozinha não encontrada com id: " + id);
    }
}