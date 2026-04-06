package com.algaworks.algafood.api.DTO.FotoProdutoDTO;

import lombok.Data;

@Data
public class FotoProdutoDTO {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
