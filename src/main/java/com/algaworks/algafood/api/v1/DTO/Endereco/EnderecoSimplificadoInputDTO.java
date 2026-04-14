package com.algaworks.algafood.api.v1.DTO.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class EnderecoSimplificadoInputDTO {
        @NotBlank
        private String cep;

        @NotBlank
        private String bairro;

        private String complemento;
        @NotBlank
        private String logradouro;
        @NotBlank
        private String numero;


}
