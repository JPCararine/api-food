package com.algaworks.algafoodapi2.service;

import com.algaworks.algafoodapi2.domain.exception.EmUso.EstadoEmUsoException;
import com.algaworks.algafoodapi2.domain.exception.NotFound.EstadoNotFoundException;
import com.algaworks.algafoodapi2.domain.model.Estado;
import com.algaworks.algafoodapi2.repository.CidadeRepository;
import com.algaworks.algafoodapi2.repository.CozinhaRepository;
import com.algaworks.algafoodapi2.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final CozinhaRepository cozinhaRepository;


    public List<Estado> listAll() {

        return estadoRepository.findAll();

    }

    public Estado findById(long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));
    }
    public Estado save(Estado estado) {
        Estado.builder()
                .nome(estado.getNome())
                .build();
        return estadoRepository.save(estado);
    }
    public void delete(long id) {
        if(cidadeRepository.existsByEstadoId(id)) {
            throw new EstadoEmUsoException(id);
        }
        estadoRepository.delete(findById(id));
    }

}
