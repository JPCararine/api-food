package com.algaworks.algafoodapi2.domain.exception.NotFound;

public class EstadoNotFoundException extends EntityNotFoundException {
    public EstadoNotFoundException(Long id) {
        super("Estado não encontrado com id: " + id);
    }
}