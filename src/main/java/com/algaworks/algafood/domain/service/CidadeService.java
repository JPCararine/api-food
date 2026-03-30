package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Cidade.CidadeDTO;
import com.algaworks.algafood.api.DTO.Cidade.CidadeInputDTO;
import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.domain.exception.JaExistente.EntidadeJaExistente;
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
    private final CidadeDTOAssembler cidadeDTOAssembler;
    private final CidadeInputDisassembler cidadeInputDisassembler;



    public List<CidadeDTO> listAll() {

        return cidadeRepository.findAll()
                .stream()
                .map(cidadeDTOAssembler::toDto)
                .toList();
    }

    public CidadeDTO findById(Long id) {
        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNotFoundException(id));
        return cidadeDTOAssembler.toDto(cidade);
    }
    @Transactional
    public CidadeDTO save(CidadeInputDTO cidadeInputDTO) {
        checarSeExisteNome(cidadeInputDTO.getNome(), null);

        Estado estado = estadoRepository.findById(cidadeInputDTO.getEstado().getId())
                .orElseThrow(() -> new EstadoNotFoundException(cidadeInputDTO.getEstado().getId()));

        Cidade cidade = cidadeInputDisassembler.toEntity(cidadeInputDTO, estado);


        return cidadeDTOAssembler.toDto(cidadeRepository.save(cidade));
    }
    @Transactional
    public void delete(long id) {
        Cidade cidade = cidadeRepository.findById(id)
                        .orElseThrow(() -> new CidadeNotFoundException(id));
        cidadeRepository.delete(cidade);
    }
    @Transactional
    public CidadeDTO replace(Long id, CidadeInputDTO cidadeInputDTO) {

        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNotFoundException(id));
        checarSeExisteNome(cidadeInputDTO.getNome(), cidade.getId());

        Estado estado = estadoRepository.findById(cidadeInputDTO.getEstado().getId())
                .orElseThrow(() -> new EstadoNotFoundException(cidadeInputDTO.getEstado().getId()));
        cidade.setEstado(estado);

        cidadeInputDisassembler.copyToEntity(cidadeInputDTO, cidade);



        return cidadeDTOAssembler.toDto(cidadeRepository.save(cidade));

        }
        public void checarSeExisteNome(String nome, Long id) {
            cidadeRepository.findByNome(nome)
                    .ifPresent(cidade -> {
                        if(!cidade.getId().equals(id)) {
                            throw new EntidadeJaExistente();
                        }
                    });
        }

    }

