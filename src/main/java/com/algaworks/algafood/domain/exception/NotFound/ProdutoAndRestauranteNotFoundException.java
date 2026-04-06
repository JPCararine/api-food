package com.algaworks.algafood.domain.exception.NotFound;

public class ProdutoAndRestauranteNotFoundException extends BaseEntityNotFoundException{

    public ProdutoAndRestauranteNotFoundException(Long restauranteId, Long produtoId) {
        super("Produto de id: " + produtoId + " não cadastrado em restaurante de id: " + restauranteId + ".");
    }
}
