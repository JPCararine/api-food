package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Cozinha.CozinhaInputDTO;
import com.algaworks.algafood.api.DTO.Restaurante.CozinhaDTO;
import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.exception.EmUso.CozinhaEmUsoException;
import com.algaworks.algafood.domain.exception.JaExistente.EntidadeJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.CozinhaNotFoundException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infrastructure.repository.CozinhaRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CozinhaService {

    private final CozinhaRepository cozinhaRepository;
    private final RestauranteRepository restauranteRepository;
    private final CozinhaDTOAssembler cozinhaDTOAssembler;
    private final CozinhaInputDisassembler cozinhaInputDisassembler;


    public Page<CozinhaDTO> findAll(Pageable pageable) {

        return cozinhaRepository.findAll(pageable)
                .map(cozinhaDTOAssembler::toDto);
    }

    public Page<CozinhaDTO> findByNome(String nome, Pageable pageable) {
        if(nome == null) {
            return findAll(pageable);
        }
        return cozinhaRepository.findByNomeContaining(nome, pageable)
                .map(cozinhaDTOAssembler::toDto);
    }
    public List<CozinhaDTO> findByNomeContaningIgnoreCase(String nome) {
        return cozinhaRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(cozinhaDTOAssembler::toDto)
                .toList();
    }
    public CozinhaDTO findById(Long id) {
        Cozinha cozinha = buscarCozinhaOuFalhar(id);
        return cozinhaDTOAssembler.toDto(cozinha);
    }
    @Transactional
    public CozinhaDTO save(CozinhaInputDTO cozinhaInputDTO) {
        ChecarSeExisteNome(cozinhaInputDTO.getNome(), null);


        Cozinha cozinha =  cozinhaInputDisassembler.toEntity(cozinhaInputDTO);

        return cozinhaDTOAssembler.toDto(cozinhaRepository.save(cozinha));
    }
    @Transactional
    public void delete(long id) {

        if(restauranteRepository.existsByCozinhaId(id)) {
            throw new CozinhaEmUsoException(id);
        }
        Cozinha cozinha = cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNotFoundException(id));
        cozinhaRepository.delete(cozinha);

    }
    @Transactional
    public CozinhaDTO replace(Long id, CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinha = buscarCozinhaOuFalhar(id);

        ChecarSeExisteNome(cozinhaInputDTO.getNome(), cozinha.getId());

        cozinhaInputDisassembler.copyToEntity(cozinhaInputDTO, cozinha);

        return cozinhaDTOAssembler.toDto(cozinhaRepository.save(cozinha));
    }
    public void ChecarSeExisteNome(String nome, Long id) {
        cozinhaRepository.findByNome(nome)
                .ifPresent(cozinha -> {
                    if(!cozinha.getId().equals(id)) {
                        throw new EntidadeJaExistente();
                    }
                });


    }

    private Cozinha buscarCozinhaOuFalhar(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNotFoundException(id));
    }
}
