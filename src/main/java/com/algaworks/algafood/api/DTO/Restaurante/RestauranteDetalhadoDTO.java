package com.algaworks.algafood.api.DTO.Restaurante;

import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@JsonPropertyOrder({"id", "nome", "frete",  "ativo", "aberto", "cozinha"})
@Data
public class RestauranteDetalhadoDTO {

    private Long id;

    private String nome;

    private BigDecimal frete;


    private Boolean aberto;
    private Boolean ativo;

    private Cozinha cozinha;


    private List<Produto> produtos;


    private List<FormaPagamento> formaPagamento = new ArrayList<>();

    private Endereco endereco;


}
