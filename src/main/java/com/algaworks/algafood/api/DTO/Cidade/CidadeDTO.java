package com.algaworks.algafood.api.DTO.Cidade;

import com.algaworks.algafood.api.DTO.Estado.EstadoDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
@JsonPropertyOrder({"nome", "id", "estado"})
@Data
public class CidadeDTO {

    private Long id;
    private String nome;
    private EstadoDTO estado;
}
