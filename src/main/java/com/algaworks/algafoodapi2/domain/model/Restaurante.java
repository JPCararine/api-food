package com.algaworks.algafoodapi2.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private Boolean aberto;
    private Boolean ativo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @Valid
    private Cozinha cozinha;

    @OneToMany(mappedBy = "restaurante")
    @JsonIgnore
    private List<Produto> produtos;


    @ManyToMany
    private List<FormaPagamento> formaPagamento = new ArrayList<>();

    @Embedded
    @JsonIgnore
    private Endereco endereco;

    @CreationTimestamp
    @JsonIgnore
    @Column(columnDefinition = "datetime")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @JsonIgnore
    @Column(columnDefinition = "datetime")
    private LocalDateTime dataAtualizacao;
    public Restaurante() {
    }
}

