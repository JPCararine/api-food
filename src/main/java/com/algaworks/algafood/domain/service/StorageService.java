package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface StorageService {

    void armazenar(NovaFoto novaFoto);

    @Data
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
