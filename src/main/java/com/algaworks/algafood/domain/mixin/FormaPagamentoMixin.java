package com.algaworks.algafood.domain.mixin;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class FormaPagamentoMixin {

    @JsonIgnore
    public List<Restaurante> restaurantes;
}
