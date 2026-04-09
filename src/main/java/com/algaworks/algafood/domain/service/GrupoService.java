package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Grupo.GrupoDTO;
import com.algaworks.algafood.api.DTO.Grupo.GrupoInputDTO;
import com.algaworks.algafood.api.DTO.Permissao.PermissaoDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoIdInputDTO;
import com.algaworks.algafood.api.assembler.*;
import com.algaworks.algafood.domain.exception.NotFound.GrupoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.PermissaoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoNotFoundException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infrastructure.repository.GrupoRepository;
import com.algaworks.algafood.infrastructure.repository.PermissaoRepository;
import com.algaworks.algafood.infrastructure.repository.ProdutoRepository;
import com.algaworks.algafood.infrastructure.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GrupoService {

    private final PermissaoRepository permissaoRepository;
    private final GrupoRepository grupoRepository;
    private final GrupoDTOAssembler grupoDTOAssembler;
    private final GrupoInputDTODisassembler grupoInputDTODisassembler;
    private final PermissaoDTOAssembler  permissaoDTOAssembler;



    public List<GrupoDTO> listAll() {
        return grupoRepository.findAll()
                .stream()
                .map(grupoDTOAssembler::toDTO)
                .toList();
    }
    public GrupoDTO findById(Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNotFoundException(id));
                return grupoDTOAssembler.toDTO(grupo);

    }
    @Transactional
    public GrupoDTO save (GrupoInputDTO grupoInputDTO) {


        Grupo grupo = grupoInputDTODisassembler.toEntity(grupoInputDTO);

        List<Long> ids = grupoInputDTO.getPermissoes()
                .stream()
                .map(produto -> produto.getId())
                .toList();
        List<Permissao> permissoes = permissaoRepository.findByIdIn(ids);

        if(permissoes.size() != ids.size()) {
            throw new RuntimeException("Permissões não encotradas");
        }
        grupo.getPermissoes().clear();
        grupo.getPermissoes().addAll(permissoes);

        return grupoDTOAssembler.toDTO(grupoRepository.save(grupo));


    }
    public void delete(Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNotFoundException(id));
        grupoRepository.delete(grupo);
    }
    @Transactional
    public GrupoDTO update(GrupoInputDTO grupoInputDTO, Long id) {

        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNotFoundException(id));

        grupoInputDTODisassembler.copyToEntity(grupoInputDTO, grupo);

        List<Long> ids = grupoInputDTO.getPermissoes()
                .stream()
                .map(p -> p.getId())
                .toList();
        List<Permissao> permissoes = permissaoRepository.findByIdIn(ids);

        if(permissoes.size() != ids.size()) {
            throw new RuntimeException("Permissões não encontradas");
        }
        grupo.getPermissoes().clear();
        grupo.getPermissoes().addAll(permissoes);
        return  grupoDTOAssembler.toDTO(grupoRepository.save(grupo));
    }
    public List<PermissaoDTO> listarPermissoes(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNotFoundException(grupoId));

        return grupo.getPermissoes()
                .stream()
                .map(permissaoDTOAssembler::toDTO)
                .toList();
    }
    @Transactional
    public void adicionarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNotFoundException(grupoId));

        Permissao permissao = permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNotFoundException(permissaoId));

        if(grupo.getPermissoes().contains(permissao)) {
            throw new RuntimeException("Grupo já possui essa permissão");
        }
        grupo.getPermissoes().add(permissao);
    }
    @Transactional
    public void removerPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNotFoundException(grupoId));

        Permissao permissao = permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNotFoundException(permissaoId));


        grupo.getPermissoes().remove(permissao);

    }

}
