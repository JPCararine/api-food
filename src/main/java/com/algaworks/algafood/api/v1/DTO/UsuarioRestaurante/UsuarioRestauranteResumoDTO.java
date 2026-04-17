package com.algaworks.algafood.api.v1.DTO.UsuarioRestaurante;

import com.algaworks.algafood.domain.model.Funcao;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@JsonPropertyOrder({"id", "nomeUsuario", "funcao"})
@Data
public class UsuarioRestauranteResumoDTO {

    private Long id;

    @JsonProperty("nome")
    private String nomeUsuario;

    @Enumerated(EnumType.STRING)
    private Funcao funcao;
}
