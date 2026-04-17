package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.api.v1.DTO.UsuarioRestaurante.UsuarioRestauranteResumoDTO;
import com.algaworks.algafood.api.v1.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioRestauranteDTOAssembler;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.UsuarioNotFoundException;
import com.algaworks.algafood.domain.model.Funcao;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.UsuarioRestaurante;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.UsuarioRepository;
import com.algaworks.algafood.infrastructure.repository.UsuarioRestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestauranteUsuarioService {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioDTOAssembler usuarioDTOAssembler;
    private final AlgaSecurity algaSecurity;
    private final UsuarioRestauranteRepository usuarioRestauranteRepository;
    private final UsuarioRestauranteDTOAssembler  usuarioDTOAssemblerAssembler;



    public Set<UsuarioRestauranteResumoDTO> listarUsuarios(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        return restaurante.getUsuarios().stream()
                .map(usuarioDTOAssemblerAssembler::toUsuarioResumoDTO)
                .collect(Collectors.toSet());
    }
    @Transactional
    public void adicionarCOHOST(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);
        Usuario usuarioLogado = buscarUsuarioOuFalhar(algaSecurity.getUsuarioId());
        Usuario usuario = buscarUsuarioOuFalhar(usuarioId);

        if(usuarioRestauranteRepository.existsByUsuarioIdAndRestauranteId(usuarioId, restauranteId)) {
            throw new RuntimeException("Usuário já está vinculado a este restaurante");
        }
        boolean isHost =
                usuarioRestauranteRepository.existsByUsuarioIdAndRestauranteIdAndFuncaoIn(
                        usuarioLogado.getId(),
                        restauranteId,
                        Collections.singleton(Funcao.HOST)
                );

        if(!isHost) {
            throw new RuntimeException("Você não possui permissão para executar essa ação");
        }

        UsuarioRestaurante vinculo = UsuarioRestaurante.builder()
                        .restaurante(restaurante)
                        .usuario(usuario)
                        .funcao(Funcao.COHOST)
                        .build();

        usuarioRestauranteRepository.save(vinculo);

    }
    @Transactional
    public void removerResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        Usuario usuario = buscarUsuarioOuFalhar(responsavelId);

        restaurante.getUsuarios().remove(usuario);
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
