package com.algaworks.algafood.api.DTO.Estado;

import com.algaworks.algafood.api.DTO.Cidade.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
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
