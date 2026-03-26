package com.algaworks.algafoodapi2.domain.exception.NotFound;

public class RestauranteNotFoundException extends EntityNotFoundException {
    public RestauranteNotFoundException(Long id) {
        super("Restaurante não encontrado com id: " + id);
    }
}