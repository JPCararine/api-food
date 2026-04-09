package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Grupo.GrupoDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioInputDTO;
import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioInputDTODisassambler;
import com.algaworks.algafood.domain.exception.JaExistente.EmailJaExistente;
import com.algaworks.algafood.domain.exception.JaExistente.EntidadeJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.GrupoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.UsuarioNotFoundException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.infrastructure.repository.GrupoRepository;
import com.algaworks.algafood.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnTransformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioDTOAssembler usuarioDTOAssembler;
    private final UsuarioInputDTODisassambler usuarioInputDTODisassambler;
    private final GrupoRepository grupoRepository;
    private final GrupoDTOAssembler grupoDTOAssembler;

    public List<UsuarioDTO> listAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioDTOAssembler::toDTO)
                .toList();
    }
    public UsuarioDTO findById(Long id) {
        Usuario usuario = buscarUsuarioOuFalhar(id);
        return usuarioDTOAssembler.toDTO(usuario);
    }
    @Transactional
    public UsuarioDTO save(UsuarioInputDTO usuarioInputDTO) {

        validarEmail(usuarioInputDTO.getEmail(), null);


        Usuario usuario = usuarioInputDTODisassambler.toEntity(usuarioInputDTO);

        List<Long> ids = usuarioInputDTO.getGrupos()
                .stream()
                .map(g -> g.getId())
                .toList();
        List<Grupo> grupos = grupoRepository.findAllById(ids);

        if(grupos.size() != ids.size()) {
            throw new RuntimeException("Grupos não encontrados");
        }

        usuario.getGrupos().clear();
        usuario.getGrupos().addAll(grupos);

        return  usuarioDTOAssembler.toDTO(usuarioRepository.save(usuario));
    }
    public void delete(Long id) {
        Usuario usuario = buscarUsuarioOuFalhar(id);
        usuarioRepository.delete(usuario);
    }
    @Transactional
    public UsuarioDTO update(UsuarioInputDTO usuarioInputDTO, Long id) {
        validarEmail(usuarioInputDTO.getEmail(), id);
        Usuario usuario = buscarUsuarioOuFalhar(id);

        usuarioInputDTODisassambler.copyToEntity(usuarioInputDTO, usuario);

        List<Long> ids = usuarioInputDTO.getGrupos()
                .stream()
                .map(g -> g.getId())
                .toList();
        List<Grupo> grupos = grupoRepository.findAllById(ids);
        if(grupos.size() != ids.size()) {
            throw new RuntimeException("Grupos não encontrados");
        }

        usuario.getGrupos().clear();
        usuario.getGrupos().addAll(grupos);

        return usuarioDTOAssembler.toDTO(usuarioRepository.save(usuario));
    }
    public void validarEmail(String email, Long id) {
        usuarioRepository.findByEmail(email)
                .ifPresent(usuario -> {
                    if(!usuario.getId().equals(id)) {
                        throw new EmailJaExistente();
                    }
                });

    }
    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String senhaNova) {
        Usuario usuario = buscarUsuarioOuFalhar(id);
        if(!usuario.getSenha().equals(senhaAtual)) {
            throw new RuntimeException("Senha atual não coincide com a senha do usuário");
        }
        usuario.setSenha(senhaNova);
    }

    public void login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha incorretos"));
        if(!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Email ou senha incorretos");
        }

    }
    public List<GrupoDTO> listarGrupos(Long usuarioId) {
        Usuario usuario = buscarUsuarioOuFalhar(usuarioId);

        return usuario.getGrupos()
                .stream()
                .map(grupoDTOAssembler::toDTO)
                .toList();
    }
    @Transactional
    public void adicionarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarUsuarioOuFalhar(usuarioId);

        Grupo grupo = buscarGrupoOuFalhar(grupoId);

        if(usuario.getGrupos().contains(grupo)) {
            throw new RuntimeException("Usuário já está nesse grupo");
        }

        usuario.getGrupos().add(grupo);
    }
    @Transactional
    public void removerGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarUsuarioOuFalhar(usuarioId);

        Grupo grupo = buscarGrupoOuFalhar(grupoId);


        usuario.getGrupos().remove(grupo);
    }

    private Grupo buscarGrupoOuFalhar(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNotFoundException(id));
    }
    private Usuario buscarUsuarioOuFalhar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }


}
