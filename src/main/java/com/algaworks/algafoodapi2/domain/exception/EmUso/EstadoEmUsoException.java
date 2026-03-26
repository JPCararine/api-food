package com.algaworks.algafoodapi2.domain.exception.EmUso;

public class EstadoEmUsoException extends EntidadeEmUsoException {
    public EstadoEmUsoException(Long id) {
        super("Estado de " + id + " não pode ser deletado, pois está em uso");
    }


    }


