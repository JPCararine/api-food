package com.algaworks.algafood.core.storage;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties("algafood.storage")
@Data
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();

    @Data
    public class Local {
        private Path diretorioFotos;
    }
    @Data
    public class S3 {


        private String idChaveAcesso;
        private String idChaveAcessoSecreta;
        private String bucket;
        private String regiao;
        private String diretorioFotos;
    }
}
