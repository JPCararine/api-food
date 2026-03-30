package com.algaworks.algafood.domain.exception.JaExistente;

public class EntidadeJaExistente extends BaseNegocioJaExistenteException {
    public EntidadeJaExistente() {

        super("Nome já está sendo utilizado");
    }
}
