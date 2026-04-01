package com.algaworks.algafood.domain.exception.NotFound;

public class UsuarioNotFoundException extends BaseEntityNotFoundException {
    public UsuarioNotFoundException(Long id) {
        super("Usuário de id " + id + " não encontrado");
    }
}
