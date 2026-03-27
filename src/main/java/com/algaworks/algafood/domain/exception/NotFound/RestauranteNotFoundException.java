package com.algaworks.algafood.domain.exception.NotFound;

public class RestauranteNotFoundException extends BaseEntityNotFoundException {
    public RestauranteNotFoundException(Long id) {
        super("Restaurante não encontrado com id: " + id);
    }
}