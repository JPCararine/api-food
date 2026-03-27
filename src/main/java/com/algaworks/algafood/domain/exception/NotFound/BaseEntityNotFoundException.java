package com.algaworks.algafood.domain.exception.NotFound;


public class BaseEntityNotFoundException extends RuntimeException{

    public BaseEntityNotFoundException(String mensagem){
        super(mensagem);
    }
}
