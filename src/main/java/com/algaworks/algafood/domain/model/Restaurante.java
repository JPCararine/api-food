package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;


    private Boolean ativo = Boolean.TRUE;


    @ManyToOne
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @Valid
    private Cozinha cozinha;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos;


    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
                joinColumns = @JoinColumn(name = "restaurante_id"),
                inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formaPagamentos = new ArrayList<>();

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;
    public Restaurante() {
    }

}

