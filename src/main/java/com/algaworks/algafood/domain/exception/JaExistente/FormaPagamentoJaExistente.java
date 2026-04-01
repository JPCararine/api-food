package com.algaworks.algafood.domain.exception.JaExistente;

public class FormaPagamentoJaExistente extends BaseNegocioJaExistenteException {
    public FormaPagamentoJaExistente() {
        super("Esse restaurante já possui essa forma de pagamento");
    }
}
