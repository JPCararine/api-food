package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.JaExistente.FormaPagamentoJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.FormaPagamentoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.FormaPagamentoRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteFormaPagamentoService {

    private final RestauranteRepository restauranteRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;


    @Transactional
    public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {

        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        FormaPagamento formaPagamento = buscarFormaPagamentoOuFalhar(formaPagamentoId);

        restaurante.getFormaPagamentos().remove(formaPagamento);

    }
    @Transactional
    public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {

        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        FormaPagamento formaPagamento = buscarFormaPagamentoOuFalhar(formaPagamentoId);

        if(restaurante.getFormaPagamentos().contains(formaPagamento)) {
            throw new FormaPagamentoJaExistente();
        }
        restaurante.getFormaPagamentos().add(formaPagamento);
    }
    public List<String> listarFormaPagamentos(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);


        return restaurante.getFormaPagamentos()
                .stream()
                .map(f -> f.getDescricao())
                .toList();

    }
    private Restaurante buscarRestauranteOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
    }
    private FormaPagamento buscarFormaPagamentoOuFalhar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(id));
    }
}
