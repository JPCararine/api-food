package com.algaworks.algafoodapi2.core.jackson;

import com.algaworks.algafoodapi2.domain.mixin.*;
import com.algaworks.algafoodapi2.domain.model.*;
import org.springframework.stereotype.Component;
import tools.jackson.databind.module.SimpleModule;

@Component
public class customJacksonMixinModule extends SimpleModule {

    public customJacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Estado.class, EstadoMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(FormaPagamento.class, FormaPagamentoMixin.class);
        setMixInAnnotation(Permissao.class, PermissaoMixin.class);
        setMixInAnnotation(Endereco.class, EnderecoMixin.class);
    }
}
