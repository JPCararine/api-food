package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Permissao.PermissaoDTO;
import com.algaworks.algafood.api.DTO.Permissao.UsuarioInputDTO;
import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.assembler.PermissaoInputDTODisassembler;
import com.algaworks.algafood.domain.exception.NotFound.PermissaoNotFoundException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.infrastructure.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;
    private final PermissaoDTOAssembler permissaoDTOAssembler;
    private final PermissaoInputDTODisassembler permissaoInputDTODisassembler;

    public List<PermissaoDTO> listAll() {
        return permissaoRepository.findAll()
                .stream()
                .map(permissaoDTOAssembler::toDTO)
                .toList();
    }
    public PermissaoDTO findById(Long id) {
        Permissao permissao = permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNotFoundException(id));
        return permissaoDTOAssembler.toDTO(permissao);
    }
    public PermissaoDTO save(UsuarioInputDTO permissaoInputDTO) {

        Permissao permissao = permissaoInputDTODisassembler.toEntity(permissaoInputDTO);

        return permissaoDTOAssembler.toDTO(permissaoRepository.save(permissao));
    }
    public PermissaoDTO update(UsuarioInputDTO permissaoInputDTO, Long id) {

        Permissao permissao = permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNotFoundException(id));

        permissaoInputDTODisassembler.copyToEntity(permissaoInputDTO, permissao);

        return permissaoDTOAssembler.toDTO(permissaoRepository.save(permissao));
    }
    public void delete(Long id) {
        Permissao permissao = permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNotFoundException(id));
        permissaoRepository.delete(permissao);
    }
}
