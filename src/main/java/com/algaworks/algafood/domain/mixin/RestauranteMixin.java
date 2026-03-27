package com.algaworks.algafood.domain.mixin;


import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestauranteMixin {


    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;


    @JsonIgnore
    private List<Produto> produtos;


    @JsonIgnore
    private List<FormaPagamento> formaPagamento = new ArrayList<>();


    @JsonIgnore
    private Endereco endereco;


   // @JsonIgnore
    private OffsetDateTime dataCadastro;


   // @JsonIgnore
    private OffsetDateTime dataAtualizacao;
}
