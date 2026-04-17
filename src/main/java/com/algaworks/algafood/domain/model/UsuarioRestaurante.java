package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario_restaurante")
@Builder
@AllArgsConstructor
public class UsuarioRestaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Restaurante restaurante;

    @Enumerated(EnumType.STRING)
    private Funcao funcao;

    public UsuarioRestaurante() {

    }
}
