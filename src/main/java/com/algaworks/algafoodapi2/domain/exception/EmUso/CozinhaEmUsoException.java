package com.algaworks.algafoodapi2.domain.exception.EmUso;

public class CozinhaEmUsoException extends EntidadeEmUsoException {
    public CozinhaEmUsoException(Long id) {
        super("Cozinha de " + id + " não pode ser deletada, pois está em uso");
    }
}
