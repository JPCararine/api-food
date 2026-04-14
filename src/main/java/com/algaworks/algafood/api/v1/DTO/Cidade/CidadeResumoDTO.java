package com.algaworks.algafood.api.v1.DTO.Cidade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
@JsonPropertyOrder({"id", "nome"})
@Data
public class CidadeResumoDTO {

    private Long id;

    private String nome;
    @JsonProperty("estado")
    private String nomeEstado;
}
