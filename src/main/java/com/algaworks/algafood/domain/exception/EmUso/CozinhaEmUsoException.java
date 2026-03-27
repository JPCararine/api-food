package com.algaworks.algafood.domain.exception.EmUso;

public class CozinhaEmUsoException extends BaseEntidadeEmUsoException {
    public CozinhaEmUsoException(Long id) {
        super("Cozinha de " + id + " não pode ser deletada, pois está em uso");
    }
}
