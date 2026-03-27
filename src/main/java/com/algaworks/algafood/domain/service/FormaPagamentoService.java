package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NotFound.FormaPagamentoNotFoundException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.infrastructure.repository.FormaPagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FormaPagamentoService {

    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> findAll(){
        return formaPagamentoRepository.findAll();
    }
    @Transactional
    public FormaPagamento save(FormaPagamento formaPagamentoRequest) {
        FormaPagamento forma = FormaPagamento.builder()
                .descricao(formaPagamentoRequest.getDescricao())
                .build();
        return formaPagamentoRepository.save(forma);
    }

    public FormaPagamento findById(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(id));
    }
    @Transactional
    public void delete(Long id) {
        formaPagamentoRepository.deleteById(id);
    }
}
