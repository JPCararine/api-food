package com.algaworks.algafood.domain.exception.EmUso;

public class FormaEmUsoException extends BaseEntidadeEmUsoException{
    public FormaEmUsoException(Long id) {

        super("Forma de id " + id + " não pode ser deletada, pois está em uso");
    }
}
