CREATE TABLE usuario_restaurante (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,

                                     usuario_id BIGINT NOT NULL,
                                     restaurante_id BIGINT NOT NULL,

                                     papel VARCHAR(20) NOT NULL,

                                     CONSTRAINT fk_usuario_restaurante_usuario
                                         FOREIGN KEY (usuario_id) REFERENCES usuario (id),

                                     CONSTRAINT fk_usuario_restaurante_restaurante
                                         FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),

                                     CONSTRAINT uk_usuario_restaurante UNIQUE (usuario_id, restaurante_id)
);