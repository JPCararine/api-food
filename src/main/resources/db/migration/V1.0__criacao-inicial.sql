

create table estado (
                        id bigint not null auto_increment,
                        nome varchar(80) not null,
                        primary key (id)
) engine=InnoDB;

create table cidade (
                        id bigint not null auto_increment,
                        nome varchar(80) not null,
                        estado_id bigint not null,
                        primary key (id),
                        constraint fk_cidade_estado
                            foreign key (estado_id) references estado(id)
) engine=InnoDB;

create table cozinha (
                         id bigint not null auto_increment,
                         nome varchar(60) not null,
                         primary key (id)
) engine=InnoDB;

create table restaurante (
                             id bigint not null auto_increment,
                             nome varchar(255) not null,
                             taxa_frete decimal(10,2) not null,
                             ativo boolean not null,
                             aberto boolean not null,
                             data_cadastro datetime,
                             data_atualizacao datetime,
                             cozinha_id bigint not null,
                             cidade_id bigint not null,
                             endereco_logradouro varchar(255),
                             endereco_numero varchar(50),
                             endereco_complemento varchar(255),
                             endereco_bairro varchar(255),
                             endereco_cep varchar(20),
                             primary key (id),
                             constraint fk_restaurante_cozinha
                                 foreign key (cozinha_id) references cozinha(id),
                             constraint fk_restaurante_cidade
                                 foreign key (cidade_id) references cidade(id)
) engine=InnoDB;

create table forma_pagamento (
                                 id bigint not null auto_increment,
                                 descricao varchar(60) not null,
                                 primary key (id)
) engine=InnoDB;

create table produto (
                         id bigint not null auto_increment,
                         nome varchar(255) not null,
                         descricao varchar(255),
                         preco decimal(10,2) not null,
                         ativo boolean not null,
                         restaurante_id bigint not null,
                         primary key (id),
                         constraint fk_produto_restaurante
                             foreign key (restaurante_id) references restaurante(id)
) engine=InnoDB;

create table permissao (
                           id bigint not null auto_increment,
                           nome varchar(255) not null,
                           descricao varchar(255),
                           primary key (id)
) engine=InnoDB;

create table grupo (
                       id bigint not null auto_increment,
                       nome varchar(255) not null,
                       primary key (id)
) engine=InnoDB;

create table usuario (
                         id bigint not null auto_increment,
                         nome varchar(255) not null,
                         email varchar(255) not null,
                         senha varchar(255) not null,
                         data_cadastro datetime,
                         primary key (id)
) engine=InnoDB;


create table restaurante_forma_pagamento (
                                             restaurante_id bigint not null,
                                             forma_pagamento_id bigint not null,
                                             primary key (restaurante_id, forma_pagamento_id),
                                             constraint fk_rf_restaurante
                                                 foreign key (restaurante_id) references restaurante(id),
                                             constraint fk_rf_forma_pagamento
                                                 foreign key (forma_pagamento_id) references forma_pagamento(id)
) engine=InnoDB;

create table grupo_permissao
(
    grupo_id     bigint not null,
    permissao_id bigint not null,
    primary key (grupo_id, permissao_id),
    constraint fk_gp_grupo
        foreign key (grupo_id) references grupo (id),
    constraint fk_gp_permissao
        foreign key (permissao_id) references permissao (id)
);