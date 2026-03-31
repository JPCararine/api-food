package com.algaworks.algafood.api.DTO.Cidade;

import com.algaworks.algafood.api.DTO.Estado.EstadoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@JsonPropertyOrder({"id", "nome"})
@Data
public class CidadeResumoDTO {

    private Long id;

    private String nome;
    @JsonProperty("estado")
    private String nomeEstado;
}
