package com.algaworks.algafood.domain.mixin;

import com.algaworks.algafood.domain.model.Cidade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class EnderecoMixin {

    @JsonIgnoreProperties("hibernateLazyInitializer")
    private Cidade cidade;
}
