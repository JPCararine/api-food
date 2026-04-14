package com.algaworks.algafoodauth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Usuario {

    @Id
    private Long id;

    private String email;
    private String senha;

    @ManyToMany
    private List<Grupo> grupo;
}