package com.algaworks.algafood.domain.exception.NotFound;

public class UsuarioEmailNotFound extends BaseEntityNotFoundException{
    public UsuarioEmailNotFound(String email) {
        super("Usuário não encontrado");
    }
}
