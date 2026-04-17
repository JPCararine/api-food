package com.algaworks.algafood.core.security;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar { }

    }
    public @interface Restaurantes {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + " (hasAuthority('EDITAR_RESTAURANTES') or "
                + "@algaSecurity.podeGerenciarRestaurante(#restauranteId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + " (hasAuthority('EDITAR_RESTAURANTES') or "
                + "@algaSecurity.hostRestaurante(#restauranteId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeAdicionarCargos { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + " (hasAuthority('EDITAR_RESTAURANTES') or "
                + "@algaSecurity.hostRestaurante(#restauranteId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeDeletarRestaurante { }



    }
    public @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeCriar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and "
                + "(hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.podeConsultarEGerenciarPedidos(#restauranteId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + " (hasAuthority('GERENCIAR_PEDIDOS') or "
                + "@algaSecurity.podeConsultarEGerenciarPedidos(#restauranteId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciar { }



    }
}
