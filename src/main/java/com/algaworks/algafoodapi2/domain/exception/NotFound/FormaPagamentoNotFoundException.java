package com.algaworks.algafoodapi2.domain.exception.NotFound;

public class FormaPagamentoNotFoundException extends EntityNotFoundException {
    public FormaPagamentoNotFoundException(Long id) {
        super("Forma de pagamento não encontrada com id: " + id);
    }
}