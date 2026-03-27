package com.algaworks.algafoodapi2.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@Entity
public class Pedido {
    public Pedido() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    @CreationTimestamp
    private OffsetDateTime dataCriacao;
    @UpdateTimestamp
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurante restaurante;
    @ManyToOne
    private Usuario usuario;

    @Embedded
    private Endereco endereco;
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;
    @ManyToOne
    private FormaPagamento formaPagamento;

    private StatusPedido status;




}
