package com.algaworks.algafood.domain.mixin;

import com.algaworks.algafood.domain.model.Cidade;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class EstadoMixin {

    @JsonIgnore
    private List<Cidade> cidades;
}
