package com.algaworks.algafood.domain.exception.NotFound;

public class GrupoNotFoundException extends BaseEntityNotFoundException {
    public GrupoNotFoundException(Long id) {

        super("Grupo de id: " + id + " não encontrado.");
    }
}
