package com.algaworks.algafoodapi2.domain.exception.NotFound;


public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String mensagem){
        super(mensagem);
    }
}
