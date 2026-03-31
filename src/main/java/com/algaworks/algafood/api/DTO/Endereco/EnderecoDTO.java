package com.algaworks.algafood.api.DTO.Endereco;

import com.algaworks.algafood.api.DTO.Cidade.CidadeDTO;
import com.algaworks.algafood.api.DTO.Cidade.CidadeResumoDTO;
import com.algaworks.algafood.domain.model.Cidade;

import com.fasterxml.jackson.annotation.JsonInclude;
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
