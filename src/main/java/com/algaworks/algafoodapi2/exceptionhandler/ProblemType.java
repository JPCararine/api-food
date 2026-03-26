package com.algaworks.algafoodapi2.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ATRIBUTO_FALTANDO("Está faltando atributos no body", "/atributos-faltando"),
    PROPRIEDADE_IGNORADA("Propriedade Ignorada no Domain", "/mensagem-ignorada"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "/mensagem-incompreensivel"),
    ENTIDADE_NAO_ENCONTRADA("Nenhuma entidade foi encontrada", "/entidade-nao-encontrada"),
    ENTIDADE_EM_USO("Em uso", "/em-uso");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://algafood.com.br" + path;
    }
}
