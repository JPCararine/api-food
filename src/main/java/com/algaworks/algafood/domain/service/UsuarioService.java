package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.DTO.Grupo.GrupoDTO;
import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioDTO;
import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioInputDTODisassambler;
import com.algaworks.algafood.domain.exception.JaExistente.EmailJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.GrupoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.UsuarioNotFoundException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.infrastructure.repository.GrupoRepository;
import com.algaworks.algafood.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

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

        usuario.setSenha(passwordEncoder.encode(usuarioInputDTO.getSenha()));

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

        if(usuarioInputDTO.getSenha() != null && !usuarioInputDTO.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(usuarioInputDTO.getSenha()));
        }
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
        if(!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha atual não coincide com a senha do usuário");
        }
        usuario.setSenha(passwordEncoder.encode(senhaNova));
    }

    public void login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha incorretos"));
        if(!passwordEncoder.matches(senha, usuario.getSenha())) {
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
