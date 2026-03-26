

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
);create table cidade (estado_id bigint, id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB;
create table cozinha (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB;
create table estado (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB;
create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB;
create table forma_pagamento_restaurantes (forma_pagamento_id bigint not null, restaurantes_id bigint not null) engine=InnoDB;
create table grupo (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB;
create table grupo_produtos (grupo_id bigint not null, produtos_id bigint not null) engine=InnoDB;
create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255), primary key (id)) engine=InnoDB;
create table permissao_grupos (grupos_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table produto (ativo bit, preco decimal(38,2), id bigint not null auto_increment, restaurante_id bigint, descricao varchar(255), nome varchar(255), primary key (id)) engine=InnoDB;
create table restaurante (aberto bit, ativo bit, taxa_frete decimal(38,2), cidade_id bigint, cozinha_id bigint, data_atualizacao datetime, data_cadastro datetime, id bigint not null auto_increment, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255), primary key (id)) engine=InnoDB;
create table restaurante_forma_pagamentos (forma_pagamentos_id bigint not null, restaurante_id bigint not null) engine=InnoDB;
create table usuario (data_cadastro datetime(6), id bigint not null auto_increment, email varchar(255), nome varchar(255), senha varchar(255), primary key (id)) engine=InnoDB;
create table usuario_grupos (grupos_id bigint not null, usuario_id bigint not null) engine=InnoDB;
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id);
alter table forma_pagamento_restaurantes add constraint FKjo7emmv8e104wqxwvd5spjp9k foreign key (restaurantes_id) references restaurante (id);
alter table forma_pagamento_restaurantes add constraint FK6efkktjnmaefvfibl3dfcggq5 foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table grupo_produtos add constraint FKau0ebsh3gfl2e3lmsajwyl9cw foreign key (produtos_id) references produto (id);
alter table grupo_produtos add constraint FK6v480x8sau3j91metydb8su7q foreign key (grupo_id) references grupo (id);
alter table permissao_grupos add constraint FKaseuycgnetiacylig5avuiifs foreign key (grupos_id) references grupo (id);
alter table permissao_grupos add constraint FK5spf7k410heg2gnc47o7p8cw9 foreign key (permissao_id) references permissao (id);
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint FKnbrdf9c9165xgwuynayr85m0h foreign key (cidade_id) references cidade (id);
alter table restaurante_forma_pagamentos add constraint FKstiurb8wgdclgtb64qju387kg foreign key (forma_pagamentos_id) references forma_pagamento (id);
alter table restaurante_forma_pagamentos add constraint FKmie8be245yrfo4dgierjte3a foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupos add constraint FK92ff1v8fkwig9tqv9bk4nvi0t foreign key (grupos_id) references grupo (id);
alter table usuario_grupos add constraint FK158r9y55ufwykh675ddt8kb43 foreign key (usuario_id) references usuario (id);
