package com.algaworks.algafood.domain.mixin;

import com.algaworks.algafood.domain.model.Grupo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class PermissaoMixin {

    @JsonIgnore
    private List<Grupo> grupos;
}
