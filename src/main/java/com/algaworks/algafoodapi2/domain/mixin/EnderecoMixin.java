package com.algaworks.algafoodapi2.domain.mixin;

import com.algaworks.algafoodapi2.domain.model.Cidade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;

public class EnderecoMixin {

    @JsonIgnoreProperties("hibernateLazyInitializer")
    private Cidade cidade;
}
