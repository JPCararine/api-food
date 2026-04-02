package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

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

    private String codigo;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurante restaurante;
    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id")
    private Usuario usuario;

    @Embedded
    private Endereco endereco;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
    @ManyToOne
    private FormaPagamento formaPagamento;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    public void confirmar() {
        if (this.status != StatusPedido.CRIADO) {
            throw new RuntimeException("Pedido não pode ser confirmado");
        }

        this.status = StatusPedido.CONFIRMADO;
        this.dataConfirmacao = OffsetDateTime.now();
    }

    public void entregar() {
        if (this.status != StatusPedido.CONFIRMADO) {
            throw new RuntimeException("Pedido não pode ser entregue");
        }

        this.status = StatusPedido.ENTREGUE;
        this.dataEntrega = OffsetDateTime.now();
    }

    public void cancelar() {
        if (this.status == StatusPedido.ENTREGUE || this.status == StatusPedido.CANCELADO) {
            throw new RuntimeException("Pedido não pode ser cancelado");
        }

        this.status = StatusPedido.CANCELADO;
        this.dataCancelamento = OffsetDateTime.now();
    }

    @PrePersist
    private void gerarCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }




}
