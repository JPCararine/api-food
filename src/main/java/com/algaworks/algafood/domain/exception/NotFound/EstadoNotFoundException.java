package com.algaworks.algafood.domain.exception.NotFound;

public class EstadoNotFoundException extends BaseEntityNotFoundException {
    public EstadoNotFoundException(Long id) {
        super("Estado não encontrado com id: " + id);
    }
}