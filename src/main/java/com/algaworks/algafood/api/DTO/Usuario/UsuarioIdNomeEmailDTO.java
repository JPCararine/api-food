package com.algaworks.algafood.api.DTO.Usuario;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
@JsonPropertyOrder({"id", "nome", "email"})
@Data
public class UsuarioIdNomeEmailDTO {

    private Long id;
    private String nome;
    private String email;
}
