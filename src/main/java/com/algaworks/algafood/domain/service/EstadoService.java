package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Estado.EstadoDTO;
import com.algaworks.algafood.api.DTO.Estado.EstadoDTODetalhado;
import com.algaworks.algafood.api.DTO.Estado.EstadoInputDTO;
import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.domain.exception.EmUso.EstadoEmUsoException;
import com.algaworks.algafood.domain.exception.JaExistente.EntidadeJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.EstadoNotFoundException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.infrastructure.repository.CidadeRepository;
import com.algaworks.algafood.infrastructure.repository.CozinhaRepository;
import com.algaworks.algafood.infrastructure.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final EstadoDTOAssembler estadoDTOAssembler;
    private final EstadoInputDisassembler estadoInputDisassembler;


    public List<EstadoDTO> listAll() {

        return estadoRepository.findAll()
                .stream()
                .map(estadoDTOAssembler::toDTO)
                .toList();

    }

    public EstadoDTO findById(Long id) {
         Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));
         return estadoDTOAssembler.toDTO(estado);
    }
    public EstadoDTODetalhado findByIdDetalhado(Long id) {
        return estadoRepository.findById(id)
                .map(estadoDTOAssembler::toDTODetalhado)
                .orElseThrow(() -> new EstadoNotFoundException(id));


    }
    @Transactional
    public EstadoDTO save(EstadoInputDTO estadoInputDTO) {
        checarSeExiste(estadoInputDTO.getNome(), null);

        Estado estado = estadoInputDisassembler.toEntity(estadoInputDTO);

        return estadoDTOAssembler.toDTO(estadoRepository.save(estado));
    }
    @Transactional
    public void delete(long id) {
        if(cidadeRepository.existsByEstadoId(id)) {
            throw new EstadoEmUsoException(id);
        }
        Estado estado =  estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));
        estadoRepository.delete(estado);
    }
    @Transactional
    public EstadoDTO put(Long id, EstadoInputDTO estadoInputDTO) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException(id));
        checarSeExiste(estadoInputDTO.getNome(), estado.getId());

        estadoInputDisassembler.copyToEntity(estadoInputDTO, estado);
        return estadoDTOAssembler.toDTO(estadoRepository.save(estado));
    }
    public void checarSeExiste(String nome, Long id) {

        estadoRepository.findByNome(nome)
                .ifPresent(estadoExistente -> {
                    if(!estadoExistente.getId().equals(id)) {
                    throw new EntidadeJaExistente();
                    }
                });
                }

}
