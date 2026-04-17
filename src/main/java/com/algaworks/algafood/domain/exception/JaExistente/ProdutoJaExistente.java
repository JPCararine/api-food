package com.algaworks.algafood.domain.exception.JaExistente;

public class ProdutoJaExistente extends RuntimeException {
    public ProdutoJaExistente() {
        super("Produto já existente");
    }
}
