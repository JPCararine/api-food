package com.algaworks.algafood.api.v1.DTO.Estado;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;
@JsonPropertyOrder({"nome", "id", "cidades"})
@Data
public class EstadoDTODetalhado {

    private Long id;
    private String nome;
    private List<CidadeIdDTO> cidades;
}
