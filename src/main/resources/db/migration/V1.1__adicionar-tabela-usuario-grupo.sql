create table usuario_grupo (
                               usuario_id bigint not null,
                               grupo_id bigint not null,
                               primary key (usuario_id, grupo_id),
                               constraint fk_ug_usuario
                                   foreign key (usuario_id) references usuario(id),
                               constraint fk_ug_grupo
                                   foreign key (grupo_id) references grupo(id)
) engine=InnoDB;