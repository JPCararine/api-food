package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Usuario;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Data
    @Builder
    class Mensagem {

        @Singular
        private Set<String> destinatarios;
        @NonNull
        private String assunto;
        @NonNull
        private String mensagem;

        @Singular("parametro")
        private Map<String, Object> parametros;
    }

}
