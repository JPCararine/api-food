package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public interface StorageService {

    void armazenar(NovaFoto novaFoto);

    void deletar(String nomeArquivo);

    InputStream recuperar(String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if(nomeArquivoAntigo != null) {
            this.deletar(nomeArquivoAntigo);
        }
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    @Data
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
