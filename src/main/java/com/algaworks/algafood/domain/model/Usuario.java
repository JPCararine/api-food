package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity

@Builder
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
    public Usuario() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;
    private String email;
    private String senha;
    @CreationTimestamp
    private OffsetDateTime dataCadastro;
    @ManyToMany
    @JoinTable(name = "usuario_grupo",
                joinColumns = @JoinColumn(name = "usuario_id"),
                inverseJoinColumns = @JoinColumn(name = "grupo_id"))

    private List<Grupo> grupos = new ArrayList<>();

    public boolean senhaCorreta(String senha) {
        return getSenha().equals(senha);
    }
    public boolean senhaIncorreta(String senha) {
        return !senhaCorreta(senha);
    }
    @OneToMany
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "usuario")
    private Set<UsuarioRestaurante> restaurantes;




}
