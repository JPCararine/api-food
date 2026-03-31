package com.algaworks.algafood.api.DTO.Endereco;

import com.algaworks.algafood.api.DTO.Cidade.CidadeResumoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnderecoInputDTO {
    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;

    private String complemento;
    @NotBlank
    private String bairro;
    @NotNull
    @Valid
    private CidadeIdInputDTO cidade;
}
