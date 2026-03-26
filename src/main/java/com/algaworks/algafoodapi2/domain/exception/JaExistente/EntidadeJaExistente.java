package com.algaworks.algafoodapi2.domain.exception.JaExistente;

public class EntidadeJaExistente extends NegocioExistenteException {
    public EntidadeJaExistente() {

        super("Nome já está sendo utilizado");
    }
}
