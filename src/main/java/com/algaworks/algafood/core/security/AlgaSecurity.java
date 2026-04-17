package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.model.Funcao;
import com.algaworks.algafood.infrastructure.repository.UsuarioRestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Component
public class AlgaSecurity {
    @Autowired
    private UsuarioRestauranteRepository usuarioRestauranteRepository;


    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        String type = jwt.getClaim("type");

        if(!"user".equals(type)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas usuários podem realizar esse serviço");
        }
        return jwt.getClaim("user_id");
    }

    public boolean podeConsultarEGerenciarPedidos(Long restauranteId) {
        Long usuarioId = getUsuarioId();

        return usuarioRestauranteRepository.existsByUsuarioIdAndRestauranteIdAndFuncaoIn(usuarioId, restauranteId, List.of(Funcao.HOST, Funcao.COHOST, Funcao.COLABORADOR));
    }
    public boolean podeGerenciarRestaurante(Long restauranteId) {
        Long usuarioId = getUsuarioId();

        return usuarioRestauranteRepository.existsByUsuarioIdAndRestauranteIdAndFuncaoIn(usuarioId, restauranteId, List.of(Funcao.HOST, Funcao.COHOST));
    }
    public boolean hostRestaurante(Long restauranteId) {
        Long usuarioId = getUsuarioId();

        return usuarioRestauranteRepository.existsByUsuarioIdAndRestauranteIdAndFuncaoIn(usuarioId, restauranteId, Collections.singleton(Funcao.HOST));
    }
}
