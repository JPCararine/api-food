package com.algaworks.algafood.api.v1.DTO.Endereco;

import com.algaworks.algafood.api.v1.DTO.Cidade.CidadeResumoDTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
@JsonPropertyOrder("cidade")
@Data
public class EnderecoDTO {

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeResumoDTO cidade;


}
