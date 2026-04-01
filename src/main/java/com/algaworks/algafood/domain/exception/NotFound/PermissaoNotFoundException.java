package com.algaworks.algafood.domain.exception.NotFound;

public class PermissaoNotFoundException extends BaseEntityNotFoundException {
    public PermissaoNotFoundException(Long id) {
        super("Permissão de id: " + id + " não encontrada");
    }
}
