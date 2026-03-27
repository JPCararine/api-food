package com.algaworks.algafoodapi2.domain.mixin;

import com.algaworks.algafoodapi2.domain.model.Cidade;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class EstadoMixin {

    @JsonIgnore
    private List<Cidade> cidades;
}
