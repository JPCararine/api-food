package com.algaworks.algafood.domain.exception.NotFound;

public class FormaPagamentoNotFoundException extends BaseEntityNotFoundException {
    public FormaPagamentoNotFoundException(Long id) {
        super("Forma de pagamento não encontrada com id: " + id);
    }
}