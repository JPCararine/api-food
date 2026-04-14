package com.algaworks.algafood.api.v1.DTO.Endereco;

import lombok.Data;

@Data
public class EnderecoSimplificadoDTO {

    private String bairro;
    private String complemento;
    private String logradouro;
    private String numero;
}
