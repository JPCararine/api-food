package com.algaworks.algafood.core.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AlgaSecurity {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsuarioEmail() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        String type = jwt.getClaim("type");

        if(!"user".equals(type)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas usuários podem realizar pedidos");
        }
        return jwt.getClaim("email");
    }
}
