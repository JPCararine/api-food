package com.algaworks.algafood.domain.exception.EmUso;

public class EstadoEmUsoException extends BaseEntidadeEmUsoException {
    public EstadoEmUsoException(Long id) {
        super("Estado de " + id + " não pode ser deletado, pois está em uso");
    }


    }


