package com.algaworks.algafoodapi2.domain.mixin;

import com.algaworks.algafoodapi2.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class FormaPagamentoMixin {

    @JsonIgnore
    public List<Restaurante> restaurantes;
}
