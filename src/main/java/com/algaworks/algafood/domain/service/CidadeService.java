package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NotFound.CidadeNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.EstadoNotFoundException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.infrastructure.repository.CidadeRepository;
import com.algaworks.algafood.infrastructure.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;



    public List<Cidade> listAll() {
        return cidadeRepository.findAll();
    }

    public Cidade findById(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNotFoundException(id));
    }
    @Transactional
    public Cidade save(Cidade cidade) {
        Estado estado = estadoRepository.findById(cidade.getEstado().getId())
                        .orElseThrow(() -> new EstadoNotFoundException(cidade.getEstado().getId()));
        Cidade.builder()
                .nome(cidade.getNome())
                .estado(estado)
                .build();
        return cidadeRepository.save(cidade);
    }
    @Transactional
    public void delete(long id) {

        cidadeRepository.delete(findById(id));
    }
    @Transactional
    public Cidade replace(Long id, Cidade cidadeRequest) {
        Cidade cidade = findById(id);

        cidade.setNome(cidadeRequest.getNome());
        if(cidadeRequest.getEstado() != null) {
            Estado estado = estadoRepository.findById(cidadeRequest.getEstado().getId())
                    .orElseThrow(() -> new EstadoNotFoundException(cidadeRequest.getEstado().getId()));
            cidade.setEstado(estado);
        }
        return cidadeRepository.save(cidade);
        }

    }

