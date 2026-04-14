package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.api.v1.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.UsuarioNotFoundException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestauranteUsuarioService {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioDTOAssembler usuarioDTOAssembler;

    public Set<UsuarioIdNomeEmailDTO> listarUsuarios(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        return restaurante.getResponsaveis().stream()
                .map(usuarioDTOAssembler::toDTOIdNome)
                .collect(Collectors.toSet());
    }
    @Transactional
    public void adicionarResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        Usuario usuario = buscarUsuarioOuFalhar(responsavelId);

        if(restaurante.getResponsaveis().contains(usuario)) {
            throw new RuntimeException("Usuário já é responsável por este restaurante");
        }
        restaurante.getResponsaveis().add(usuario);
    }
    @Transactional
    public void removerResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        Usuario usuario = buscarUsuarioOuFalhar(responsavelId);

        restaurante.getResponsaveis().remove(usuario);
    }
    private Restaurante buscarRestauranteOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
    }
    private Usuario buscarUsuarioOuFalhar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }
}
