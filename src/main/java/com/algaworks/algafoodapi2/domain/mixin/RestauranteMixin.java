package com.algaworks.algafoodapi2.domain.mixin;


import com.algaworks.algafoodapi2.domain.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
