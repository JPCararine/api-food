package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.api.v1.DTO.UsuarioRestaurante.UsuarioRestauranteResumoDTO;
import com.algaworks.algafood.api.v1.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioRestauranteDTOAssembler;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.exception.NegocioException;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestauranteUsuarioService {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlgaSecurity algaSecurity;
    private final UsuarioRestauranteRepository usuarioRestauranteRepository;
    private final UsuarioRestauranteDTOAssembler usuarioDTOAssemblerAssembler;

    public Set<UsuarioRestauranteResumoDTO> listarUsuarios(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        return restaurante.getUsuarios().stream()
                .map(usuarioDTOAssemblerAssembler::toUsuarioResumoDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void adicionarCOHOST(Long restauranteId, Long usuarioId) {
        validarHost(restauranteId);

        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);
        Usuario usuario = buscarUsuarioOuFalhar(usuarioId);

        if (usuarioRestauranteRepository.existsByUsuarioIdAndRestauranteId(usuarioId, restauranteId)) {
            throw new NegocioException("Usuário já está vinculado a este restaurante");
        }

        UsuarioRestaurante vinculo = UsuarioRestaurante.builder()
                .restaurante(restaurante)
                .usuario(usuario)
                .funcao(Funcao.COHOST)
                .build();

        usuarioRestauranteRepository.save(vinculo);
    }

    @Transactional
    public void removerCOHOSTOuColaborador(Long restauranteId, Long usuarioId) {
        validarHost(restauranteId);

        if (usuarioId.equals(algaSecurity.getUsuarioId())) {
            throw new NegocioException("Você não pode remover a si mesmo");
        }

        UsuarioRestaurante vinculo = usuarioRestauranteRepository
                .findByUsuarioIdAndRestauranteId(usuarioId, restauranteId)
                .orElseThrow(() -> new NegocioException("Vínculo não encontrado"));

        if (vinculo.getFuncao() == Funcao.HOST) {
            throw new NegocioException("Não é possível remover o responsável do restaurante");
        }

        usuarioRestauranteRepository.deleteByUsuarioIdAndRestauranteId(usuarioId, restauranteId);
    }

    @Transactional
    public void adicionarCOLABORADOR(Long restauranteId, Long usuarioId) {
        validarHostOuCohost(restauranteId);

        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);
        Usuario usuario = buscarUsuarioOuFalhar(usuarioId);

        if (usuarioRestauranteRepository.existsByUsuarioIdAndRestauranteId(usuarioId, restauranteId)) {
            throw new NegocioException("Usuário já está vinculado a este restaurante");
        }

        UsuarioRestaurante vinculo = UsuarioRestaurante.builder()
                .restaurante(restaurante)
                .usuario(usuario)
                .funcao(Funcao.COLABORADOR)
                .build();

        usuarioRestauranteRepository.save(vinculo);
    }

    @Transactional
    public void removerCOLABORADOR(Long restauranteId, Long usuarioId) {
        validarHostOuCohost(restauranteId);

        if (usuarioId.equals(algaSecurity.getUsuarioId())) {
            throw new NegocioException("Você não pode remover a si mesmo");
        }

        UsuarioRestaurante vinculo = usuarioRestauranteRepository
                .findByUsuarioIdAndRestauranteId(usuarioId, restauranteId)
                .orElseThrow(() -> new NegocioException("Vínculo não encontrado"));

        if (vinculo.getFuncao() != Funcao.COLABORADOR) {
            throw new NegocioException("Não é possível remover um usuário com cargo maior ou igual ao seu");
        }

        usuarioRestauranteRepository.deleteByUsuarioIdAndRestauranteId(usuarioId, restauranteId);
    }


    private void validarHost(Long restauranteId) {
        if (!algaSecurity.hostRestaurante(restauranteId)) {
            throw new NegocioException("Você não possui permissão para executar essa ação");
        }
    }

    private void validarHostOuCohost(Long restauranteId) {
        if (!algaSecurity.podeGerenciarRestaurante(restauranteId)) {
            throw new NegocioException("Você não possui permissão para executar essa ação");
        }
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