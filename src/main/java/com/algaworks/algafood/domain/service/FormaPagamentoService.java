package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.FormaPagamento.FormaPagamentoDTO;
import com.algaworks.algafood.api.DTO.FormaPagamento.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDTODisassembler;
import com.algaworks.algafood.domain.exception.EmUso.FormaEmUsoException;
import com.algaworks.algafood.domain.exception.NotFound.FormaPagamentoNotFoundException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.infrastructure.repository.FormaPagamentoRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FormaPagamentoService {

    private FormaPagamentoRepository formaPagamentoRepository;
    private RestauranteRepository restauranteRepository;
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
    private FormaPagamentoInputDTODisassembler formaPagamentoInputDTODisassembler;

    public List<FormaPagamentoDTO> findAll(){

        return formaPagamentoRepository.findAll()
                .stream()
                .map(formaPagamentoDTOAssembler::toDTO)
                .toList();
    }
    @Transactional
    public FormaPagamentoDTO save(FormaPagamentoInputDTO formaPagamentoInputDTO) {

        FormaPagamento formaPagamento = formaPagamentoInputDTODisassembler.toEntity(formaPagamentoInputDTO);

        return formaPagamentoDTOAssembler.toDTO(formaPagamentoRepository.save(formaPagamento));
    }

    public FormaPagamentoDTO findById(Long id) {
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(id));
        return formaPagamentoDTOAssembler.toDTO(formaPagamento);
    }
    @Transactional
    public void delete(Long id) {

        if(restauranteRepository.existsByFormaPagamentosId(id)) {
            throw new FormaEmUsoException(id);
        }
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(id)
                        .orElseThrow(() -> new FormaPagamentoNotFoundException(id));
        formaPagamentoRepository.delete(formaPagamento);
    }
    @Transactional
    public FormaPagamentoDTO update(FormaPagamentoInputDTO formaPagamentoInputDTO, Long id) {
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(id)
                        .orElseThrow(() -> new FormaPagamentoNotFoundException(id));
        formaPagamentoInputDTODisassembler.copyToEntity(formaPagamentoInputDTO, formaPagamento);

        return formaPagamentoDTOAssembler.toDTO(formaPagamentoRepository.save(formaPagamento));
    }
}
