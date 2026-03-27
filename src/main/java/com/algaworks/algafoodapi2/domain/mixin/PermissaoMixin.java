package com.algaworks.algafoodapi2.domain.mixin;

import com.algaworks.algafoodapi2.domain.model.Grupo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class PermissaoMixin {

    @JsonIgnore
    private List<Grupo> grupos;
}
