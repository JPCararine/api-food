alter table restaurante drop foreign key fk_restaurante_cidade;

alter table restaurante drop column cidade_id;

alter table restaurante add column endereco_cidade_id bigint;

alter table restaurante add constraint fk_restaurante_cidade
    foreign key (endereco_cidade_id) references cidade(id);