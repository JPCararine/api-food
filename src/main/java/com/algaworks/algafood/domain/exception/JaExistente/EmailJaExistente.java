package com.algaworks.algafood.domain.exception.JaExistente;

public class EmailJaExistente extends BaseNegocioJaExistenteException{

    public EmailJaExistente() {
        super("Email já está sendo utilizado.");
    }

}
