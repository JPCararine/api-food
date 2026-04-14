package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.DTO.Grupo.GrupoDTO;
import com.algaworks.algafood.api.v1.DTO.Grupo.GrupoInputDTO;
import com.algaworks.algafood.api.v1.DTO.Permissao.PermissaoDTO;
import com.algaworks.algafood.api.v1.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.GrupoInputDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.domain.exception.NotFound.GrupoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.PermissaoNotFoundException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.infrastructure.repository.GrupoRepository;
import com.algaworks.algafood.infrastructure.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GrupoService {

    private final PermissaoRepository permissaoRepository;
    private final GrupoRepository grupoRepository;
    private final GrupoDTOAssembler grupoDTOAssembler;
    private final GrupoInputDTODisassembler grupoInputDTODisassembler;
    private final PermissaoDTOAssembler permissaoDTOAssembler;



    public List<GrupoDTO> listAll() {
        return grupoRepository.findAll()
                .stream()
                .map(grupoDTOAssembler::toDTO)
                .toList();
    }
    public GrupoDTO findById(Long id) {
        Grupo grupo = buscarGrupoOuFalhar(id);
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
        Grupo grupo = buscarGrupoOuFalhar(id);
        grupoRepository.delete(grupo);
    }
    @Transactional
    public GrupoDTO update(GrupoInputDTO grupoInputDTO, Long id) {

        Grupo grupo = buscarGrupoOuFalhar(id);

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
        Grupo grupo = buscarGrupoOuFalhar(grupoId);

        return grupo.getPermissoes()
                .stream()
                .map(permissaoDTOAssembler::toDTO)
                .toList();
    }
    @Transactional
    public void adicionarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarGrupoOuFalhar(grupoId);

        Permissao permissao = buscarPermissaoOuFalhar(permissaoId);

        if(grupo.getPermissoes().contains(permissao)) {
            throw new RuntimeException("Grupo já possui essa permissão");
        }
        grupo.getPermissoes().add(permissao);
    }
    @Transactional
    public void removerPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarGrupoOuFalhar(grupoId);

        Permissao permissao = buscarPermissaoOuFalhar(permissaoId);


        grupo.getPermissoes().remove(permissao);

    }

    private Grupo buscarGrupoOuFalhar(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNotFoundException(id));
    }
    private Permissao buscarPermissaoOuFalhar(Long id) {
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNotFoundException(id));
    }

}
