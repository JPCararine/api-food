package com.algaworks.algafoodapi2.domain.mixin;

import com.algaworks.algafoodapi2.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
